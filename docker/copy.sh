#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20220814.sql ./mysql/db
cp ../sql/ry_config_20220510.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../voya-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy voya-gateway "
cp ../voya-gateway/target/voya-gateway.jar ./voya/gateway/jar

echo "begin copy voya-auth "
cp ../voya-auth/target/voya-auth.jar ./voya/auth/jar

echo "begin copy voya-visual "
cp ../voya-visual/voya-monitor/target/voya-visual-monitor.jar  ./voya/visual/monitor/jar

echo "begin copy voya-modules-system "
cp ../voya-modules/voya-system/target/voya-modules-system.jar ./voya/modules/system/jar

echo "begin copy voya-modules-file "
cp ../voya-modules/voya-file/target/voya-modules-file.jar ./voya/modules/file/jar

echo "begin copy voya-modules-job "
cp ../voya-modules/voya-job/target/voya-modules-job.jar ./voya/modules/job/jar

echo "begin copy voya-modules-gen "
cp ../voya-modules/voya-gen/target/voya-modules-gen.jar ./voya/modules/gen/jar

