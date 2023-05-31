package com.example.orisimpl.exception;

import java.util.UUID;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(UUID groupId) {
        super(String.format("Group with this id = %s, not found", groupId));
    }
}
