@echo on
@echo =============================================================
@echo $                                                           $
@echo $               SYH galaxy-Platform                         $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Copyright (C) 2021-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title ZLT Microservices-Platform
@color 0e

call mvn clean package -Dmaven.test.skip=true

pause