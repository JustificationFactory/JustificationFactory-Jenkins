package fr.axonic.observation.binding.listener;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.event.ACollectionChangedEvent;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityEvent;
import fr.axonic.observation.event.ChangedEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class AListSimpleBindingListener<T extends AEntity> implements AEntityListener {

        private final Logger LOGGER = LoggerFactory.getLogger(AListSimpleBindingListener.class);
        private final AList<T> toBind;

        public AListSimpleBindingListener(AList<T> toBind){
            this.toBind = toBind;
        }

        @Override
        public void changed(AEntityChanged events) {

            if(events.getType().equals(ChangedEventType.PERMUTED)) {
                // copy last modification of list
                copyPermuttedAList(toBind, (AList<T>)events.getEvents().get(events.getEvents().size() -1).getSource());
            } else {
                for(AEntityEvent aEntityEvent : events.getEvents()){

                    if (aEntityEvent instanceof ACollectionChangedEvent) {
                        ACollectionChangedEvent<T> event = (ACollectionChangedEvent) aEntityEvent;

                        switch (aEntityEvent.getChangedType()) {
                            case ADDED:
                                if(event.getIndexChanged() != -1){
                                    toBind.add(event.getIndexChanged(), event.getChangedValue());
                                } else {
                                    toBind.add(event.getChangedValue());
                                }
                                break;
                            case REMOVED:
                                if(event.getIndexChanged() != -1) { // remove entity at given index
                                    toBind.remove(event.getIndexChanged());
                                } else { // or remove first occurrence
                                    toBind.remove(event.getChangedValue());
                                }
                                break;
                            case PERMUTED:
                                copyPermuttedAList(toBind, (AList<T>)event.getSource());
                                break;
                            case CHANGED:
                                if(event.getIndexChanged() != -1) {
                                    toBind.set(event.getIndexChanged(), event.getChangedValue());
                                } else {
                                    LOGGER.error(
                                            "Events typed with CHANGED requires the index of change !",
                                            new BindingParametersException()
                                    );
                                    return;
                                }
                                break;
                            default:
                                break;
                        }
                    }

                }
            }

        }

        @Override
        public boolean listenOnlyEffectiveChanges() {
            return true;
        }

        @Override
        public boolean acceptChangedType(ChangedEventType changedEventType) {
            return true;
        }

        private void copyPermuttedAList(AList<T> dest, AList<T> src){
            IntStream.range(0, src.size()).forEach(i -> dest.set(i, src.get(i)));
        }

}
