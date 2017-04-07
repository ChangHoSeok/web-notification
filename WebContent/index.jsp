<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>web-notification</title>
<script type="text/javascript">
<!--
	var title = '안내';
	var message = 'Hello World!!';
	var noti = undefined;
	var isDeniedAlertNoti = false;
	
	console.debug(typeof(Notification));
	
	if (typeof(Notification) !== 'undefined') {
		Notification.requestPermission(function(status) {
			// This allows to use Notification.permission with Chrome/Safari
			if (Notification.permission !== status) {
				Notification.permission = status;
			}
		});
		
		if (Notification && Notification.permission === "granted") {
			noti = new Notification(title, {
				body: message
			});
		} else if (Notification && Notification.permission !== "denied") {
			Notification.requestPermission(function(status) {
				if (Notification.permission !== status) {
					Notification.permission = status;
				}

				if (status === "granted") {
					noti = new Notification(title, {
						body: message
					});
				} else if (isDeniedAlertNoti) {
					alert(message);
				}
			});
		}
	}
	
	if (noti === undefined && isDeniedAlertNoti) {
		alert(message);
	}
//-->
</script>
</head>
<body>
	web-notification
</body>
</html>