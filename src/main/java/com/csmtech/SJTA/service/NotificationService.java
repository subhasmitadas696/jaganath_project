package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.csmtech.SJTA.dto.NotificationDTO;

public interface NotificationService {
	List<NotificationDTO> getAllNotifications();

	NotificationDTO getNotificationById(BigInteger notificationId);

	void addNotification( NotificationDTO notificationDTO);

	void updateNotification(@Valid NotificationDTO notificationDTO);

	void softDeleteNotification(@Valid NotificationDTO notificationDTO);

	List<NotificationDTO> getAllNotification(String description, Date startDate, Date endDate);
}
