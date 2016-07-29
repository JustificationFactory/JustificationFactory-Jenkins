package fr.axonic.observation.binding.listener;

import fr.axonic.base.engine.AVar;
import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityChangedEvent;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class AVarBiDirectionalBindingListener<T> implements AEntityListener {

        private final Logger LOGGER = LoggerFactory.getLogger(AVarBiDirectionalBindingListener.class);
        private final AVar<T> var1, var2;

        private boolean updating;

        public AVarBiDirectionalBindingListener(AVar<T> var1, AVar<T> var2){
            this.var1 = var1;
            this.var2 = var2;
        }

        @Override
        public void changed(AEntityChanged events) {

            if(!updating) {

                if(var1 == null || var2 == null){
                    if(var1 != null){
                        var1.removeListener(this);
                    }

                    if(var2 != null){
                        var2.removeListener(this);
                    }
                } else {
                    events.getEvents().stream().forEach(ev -> {

                        if(ev instanceof AEntityChangedEvent) {
                            AEntityChangedEvent<T> event = (AEntityChangedEvent<T>)ev;

                            final AVar<T> sourceVar = (AVar<T>) event.getSource();
                            final AVar<T> destVar = (var1.equals(sourceVar)) ? var2 : var1;

                            try {
                                updating = true;
                                destVar.setValue(event.getNewValue());
                            } catch (VerificationException e){
                                LOGGER.error(e.getMessage(), e);
                            } catch (RuntimeException e) {
                                try {
                                    destVar.setValue(event.getNewValue());
                                } catch (Exception e1) {
                                    e.addSuppressed(e1);

                                    var1.unbind();
                                    var2.unbind();

                                    throw new RuntimeException("Bidirectional binding failed together with an attempt" + " to restore the source property to the previous value."
                                            + " Removing the bidirectional binding from properties " +
                                            var1 + " and " + var2, e1);
                                }
                            } finally {
                                updating = false;
                            }
                        }
                    });
                }
            }

        }

        @Override
        public boolean listenOnlyEffectiveChanges() {
            return true;
        }

        @Override
        public boolean acceptChangedType(ChangedEventType changedEventType) {
            return ChangedEventType.CHANGED.equals(changedEventType);
        }

}
