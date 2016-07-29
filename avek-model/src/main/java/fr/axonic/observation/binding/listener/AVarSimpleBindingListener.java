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
public class AVarSimpleBindingListener<T> implements AEntityListener {

        private final Logger LOGGER = LoggerFactory.getLogger(AVarSimpleBindingListener.class);
        private final AVar<T> toBeBinded;

        public AVarSimpleBindingListener(AVar<T> toBeBinded){
            this.toBeBinded = toBeBinded;
        }

        @Override
        public void changed(AEntityChanged events) {
            if(events.getEvents().size() == 1 && events.getEvents().get(0) instanceof AEntityChangedEvent){
                try {
                    AEntityChangedEvent<T> event = (AEntityChangedEvent<T>) events.getEvents().get(0);
                    toBeBinded.setValue(event.getNewValue());
                } catch (VerificationException | ClassCastException e) {
                    LOGGER.error(e.getMessage(), e);
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
