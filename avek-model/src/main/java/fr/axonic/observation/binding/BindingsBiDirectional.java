package fr.axonic.observation.binding;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;
import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.listener.AListBiDirectionalBindingListener;
import fr.axonic.observation.binding.listener.AVarBiDirectionalBindingListener;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class BindingsBiDirectional {

    private static void checkParameters(AEntity entity1, AEntity entity2) throws BindingTypesException {
        if ((entity1 == null) || (entity2 == null)) {
            throw new NullPointerException("Both properties must be specified.");
        }

        if (entity1 == entity2) {
            throw new IllegalArgumentException("Cannot bind property to itself");
        }

        if(!(entity1 instanceof AVar && entity2 instanceof AVar)
                && !(entity1 instanceof AList && entity2 instanceof AList)
                && !(entity1 instanceof AStructure && entity2 instanceof AStructure)){
            throw new BindingTypesException("Entities you tried to bind doesn't have the same type...");
        }
    }


    public static void bindBiDirectional(AEntity entity1, AEntity entity2)
            throws BindingTypesException, VerificationException, BindingParametersException {

        checkParameters(entity1, entity2);

        if(!entity1.isBindWith(entity2) && !entity2.isBindWith(entity1)) {

            final AEntityListener listener = ((entity1 instanceof AList && entity2 instanceof AList) ?
                    new AListBiDirectionalBindingListener<>((AList) entity1, (AList) entity2) :
                    (entity1 instanceof AVar && entity2 instanceof AVar) ?
                            new AVarBiDirectionalBindingListener<>((AVar) entity1, (AVar) entity2) :
                            null);

            if (listener != null) {

                entity1.setValues(entity2);

                entity1.addListener(listener);
                entity1.setBindingListener(listener);
                entity1.setBindElement(entity2);

                entity2.addListener(listener);
                entity2.setBindingListener(listener);
                entity2.setBindElement(entity1);

            } else {
                throw new BindingTypesException(
                        "Given entities cannot be bind together as their types are not compatible.");
            }

        } else {
            throw new BindingParametersException("One of the given entity is already bind. An entity cannot bind more than one other entity.");
        }
    }

}
