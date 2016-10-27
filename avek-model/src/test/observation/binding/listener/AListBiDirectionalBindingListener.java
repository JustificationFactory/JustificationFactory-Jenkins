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
public class AListBiDirectionalBindingListener<T extends AEntity> implements AEntityListener {

        private final Logger LOGGER = LoggerFactory.getLogger(AListBiDirectionalBindingListener.class);
        private final AList<T> list1, list2;

        private boolean updating;

        public AListBiDirectionalBindingListener(AList<T> list1, AList<T> list2){
            this.list1 = list1;
            this.list2 = list2;
        }

        @Override
        public void changed(AEntityChanged events) {

            if(!updating) {

                if(list1 == null || list2 == null){
                    if(list1 != null){
                        list1.removeListener(this);
                    }

                    if(list2 != null){
                        list2.removeListener(this);
                    }
                } else {

                    if (events.getType().equals(ChangedEventType.PERMUTED)) {
                        // copy last modification of list
                        final AList<T> sourceList = (AList<T>) events.getEvents().get(events.getEvents().size() - 1).getSource();
                        final AList<T> destList = (list1.equals(sourceList)) ? list2 : list1;

                        try {
                            updating = true;
                            copyPermuttedAList(destList, sourceList);
                        } catch (RuntimeException e) {
                            try {
                                copyPermuttedAList(destList, sourceList);
                            } catch (Exception e1) {
                                e.addSuppressed(e1);

                                list1.unbind();
                                list2.unbind();

                                throw new RuntimeException("Bidirectional binding failed together with an attempt" + " to restore the source property to the previous value."
                                        + " Removing the bidirectional binding from properties " +
                                        list1 + " and " + list2, e1);
                            }
                        } finally {
                            updating = false;
                        }

                    } else {
                        for (AEntityEvent aEntityEvent : events.getEvents()) {

                            if (aEntityEvent instanceof ACollectionChangedEvent) {
                                ACollectionChangedEvent<T> event = (ACollectionChangedEvent) aEntityEvent;

                                final AList<T> sourceList = (AList<T>) aEntityEvent.getSource();
                                final AList<T> destList = (list1 == sourceList) ? list2 : list1;

                                try {

                                    updating = true;
                                    updateList(destList, event);

                                } catch (RuntimeException e) {
                                    try {

                                        updateList(destList, event);

                                    } catch (Exception e1) {
                                        e.addSuppressed(e1);

                                        list1.unbind();
                                        list2.unbind();

                                        throw new RuntimeException(
                                                "Bidirectional binding failed together with an attempt"
                                                        + " to restore the source property to the previous value."
                                                        + " Removing the bidirectional binding from properties " +
                                                        list1 + " and " + list2, e1);
                                    }
                                } finally {
                                    updating = false;
                                }

                            }

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

        private void updateList(final AList<T> destList, ACollectionChangedEvent<T> event){
            switch (event.getChangedType()) {
                case ADDED:
                    if (event.getIndexChanged() != -1) {
                        destList.add(event.getIndexChanged(), event.getChangedValue());
                    } else {
                        destList.add(event.getChangedValue());
                    }
                    break;
                case REMOVED:
                    if (event.getIndexChanged() != -1) { // remove entity at given index
                        destList.remove(event.getIndexChanged());
                    } else { // or remove first occurrence
                        destList.remove(event.getChangedValue());
                    }
                    break;
                case PERMUTED:
                    copyPermuttedAList(destList, (AList<T>) event.getSource());
                    break;
                case CHANGED:
                    if (event.getIndexChanged() != -1) {
                        destList.set(event.getIndexChanged(), event.getChangedValue());
                    } else {
                        LOGGER.error("Events typed with CHANGED requires the index of change !", new BindingParametersException());
                        return;
                    }
                    break;
                default:
                    break;
            }
        }

}
