/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gang.client.po;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Table("carbooking")
public class CarBooking {
  @Id
  private Integer id;
  private String name;
  private Integer amount;
  private Boolean confirmed;
  private Boolean cancelled;
  private String uuid;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public void confirm() {
    this.confirmed = true;
    this.cancelled = false;
  }

  boolean isCancelled() {
    return cancelled;
  }

  public void cancel() {
    this.confirmed = false;
    this.cancelled = true;
  }
}
