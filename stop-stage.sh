#/bin/bash

cd target/universal/stage

if [[ -f RUNNING_PID ]] && ps -p $(cat RUNNING_PID) > /dev/null 2>&1
then
   kill $(cat RUNNING_PID)
   rm RUNNING_PID
   echo "Stage Stopped.."
else
   echo "Not Running...."
fi

exit 0