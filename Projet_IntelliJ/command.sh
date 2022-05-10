#in one terminal, start the log generator:
python3 log-generator.py
#or (Customized by me) :
#python3 log-generator2.py
# in a 2nd terminal, watch the output from the logs:
# every 5 seconds, you should see new entries
tail -f /tmp/log-generator.log > /home/hdsaad/Documents/simple/output/mydata.txt

