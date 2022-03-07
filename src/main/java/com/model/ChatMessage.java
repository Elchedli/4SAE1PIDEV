package com.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ChatMessage {
	MessageType type;
	String content;
	String sender;
	String time;
}
