/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.gang.adzure.comgangcaseadzure.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

@JsonPropertyOrder({"ID", "Description", "Owner"})
public class TodoItem {
    private String description;
    private int id;
    private String owner;

    @JsonCreator
    public TodoItem(
            @JsonProperty("ID") int id,
            @JsonProperty("Description") String description,
            @JsonProperty("Owner") String owner
    ) {
        this.description = description;
        this.id = id;
        this.owner = owner;
    }

    @JsonGetter("Description")
    public String getDescription() {
        return description;
    }

    @JsonSetter("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonGetter("Owner")
    public String getOwner() {
        return owner;
    }

    @JsonSetter("Owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonGetter("ID")
    public int getID() {
        return id;
    }

    @JsonSetter("ID")
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof TodoItem)) {
            return false;
        }
        final TodoItem group = (TodoItem) o;
        return this.getDescription().equals(group.getDescription())
                && this.getOwner().equals(group.getOwner())
                && this.getID() == group.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, owner);
    }
}

