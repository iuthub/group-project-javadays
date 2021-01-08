package dao;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN    (0),
    LIBRARIAN(1),
    STUDENT  (2);

    private final int value;
    private static final Map<Integer, Role> rolesMap = new HashMap<>();

    Role(int value) {
        this.value = value;
    }

    static {
        for (Role roleType : Role.values()) {
            rolesMap.put(roleType.value, roleType);
        }
    }

    public static Role valueOf(int roleType) {
        return rolesMap.get(roleType);
    }

    public int getValue() {
        return value;
    }
}
