if ps -p $(cat ./mb.pid) > /dev/null 2>&1
then
   kill $(cat ./mb.pid)
else
   echo "No Process Running"
fi

exit 0