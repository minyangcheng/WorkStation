@echo off
echo 是否将结果上传到svn服务器?
pause
svn add */*
svn commit -m "add files"
echo 操作完成
pause