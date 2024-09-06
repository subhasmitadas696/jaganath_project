package com.csmtech.SJTA.dto;

import java.math.BigInteger;
import java.util.Date;

import com.csmtech.SJTA.entity.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	public NotificationDTO(Notification notification) {
		this.notificationId = notification.getNotificationId();
		this.title = notification.getTitle();
		this.description = notification.getDescription();
		this.uploadDoc = notification.getUploadDoc();
		this.createdOn = notification.getCreatedOn();
	}

	private BigInteger notificationId;
	private String title;
	private String description;
	private String uploadDoc;
	private Date createdOn;
	private Boolean status;
	private BigInteger createdBy;
	private BigInteger updatedBy;
	private Date updatedOn;
	private Character publish;
	private Date startDate;
	private Date endDate;
}