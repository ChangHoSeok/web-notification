/*
*
*  Push Notifications codelab
*  Copyright 2015 Google Inc. All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      https://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License
*
*/

/* eslint-env browser, serviceworker, es6 */

'use strict';

importScripts('/webjars/json2/20140204/json2.min.js');

let notiData = {};

self.addEventListener('push', function(event) {
	let title = 'Push Title';
	const options = {
		body: 'Yay it works.',
		icon: '/images/icon.png',
		badge: '/images/badge.png'
	};
	
	if (event.data) {
		try {
			notiData = JSON.parse(event.data.text());
			
			if (notiData.title !== '') {
				title = notiData.title;
			}
			
			if (notiData.message !== '') {
				options.body = notiData.message;
			}

			if (notiData.icon !== '') {
				options.icon = notiData.icon;
			}
			
			if (notiData.badge !== '') {
				options.badge = notiData.badge;
			}
			
			event.waitUntil(self.registration.showNotification(title, options));
		} catch (error) {
			console.info(error);
		}
	}
});

self.addEventListener('notificationclick', function(event) {
	event.notification.close();
	
	if (notiData.link && notiData.link !== '') {
		event.waitUntil(
			clients.matchAll({type: 'window'}).then(windowClients => {
	            // Check if there is already a window/tab open with the target URL
	            for (let i = 0; i < windowClients.length; i++) {
	                let client = windowClients[i];
	                if (client.url === notiData.link && 'focus' in client) {
	                    return client.focus();
	                }
	            }
	            
	            // If not, then open the target URL in a new window/tab.
	            if (clients.openWindow) {
	                return clients.openWindow(notiData.link);
	            }
	        })
		);
	}
});