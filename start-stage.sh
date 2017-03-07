#/bin/bash

cd target/universal/stage

if [[ -f RUNNING_PID ]] && ps -p $(cat RUNNING_PID) > /dev/null 2>&1
then
   echo "Already Stage Running... $(cat RUNNING_PID)"
else
   bin/nxt_api_proxy &
   echo "Stage Started.."
fi

exit 0