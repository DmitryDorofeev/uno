#!/bin/sh
### BEGIN INIT INFO
# Provides: uno
# Required-Start: 0 1 6
# Required-Stop: 0 1 6
# Default-Start: 0 1 6
# Default-Stop: 0 1 6
# Short-Description: Uno Game Server
### END INIT INFO

set -e

export LOG="/var/log/uno.log"
export DAEMON="/usr/lib/azaz.log"
export PIDFILE="/var/run/uno.pid"
export UID=$(id -u uno)
export DAEMON_OPTS="--ini=/etc/mail-app/wsgi.ini --module=application.wsgi:application --daemonize=${LOG} --chdir=/usr/lib/mail-app --pidfile=${PIDFILE} --uid=${UID}"
export PATH="${PATH:+$PATH:}/usr/sbin:/sbin"

start() {
	${DAEMON} ${DAEMON_OPTS}
}

stop() {
	${DAEMON} --stop ${PIDFILE}
}

case "$1" in

start)
	if [ -e ${PIDFILE} ]; then	
		if [ -e /proc/$(cat ${PIDFILE}) ]; then
			echo 'Service already started'
			exit 1
		fi
	fi
	echo -n "Starting daemon: uno"
	start
	;;
stop)
	echo -n "Stopping daemon: mail-app"
	stop
	;;
restart|reload)
	echo -n "Restarting daemon: mail-app"
	stop || true
	start
	;;
*)
	echo "Usage: "$1" {start|stop|restart}"
	exit 1
esac
exit 0

