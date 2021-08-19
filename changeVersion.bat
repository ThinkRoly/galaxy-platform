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

@title SYH galaxy-Platform
@color 0e

set /p version=«Î ‰»Î∞Ê±æ∫≈:

call mvn versions:set -DnewVersion=%version%

call mvn versions:commit

pause