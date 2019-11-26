1. touch /etc/rc.d/init.d/timeCalibration
2. vi /etc/rc.d/init.d/timeCalibration
3. 赋予权限
chmod +x /etc/rc.d/init.d/alarmGather
4. 设置开机启动
chkconfig --add timeCalibration
5. 启动服务
systemctl start timeCalibration
