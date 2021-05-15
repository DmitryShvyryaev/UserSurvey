package ru.usersSurvey.util;

import ru.usersSurvey.model.AbstractEntity;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new.");
        }
    }

    public static void assureIdConsistent(AbstractEntity entity, long id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}
