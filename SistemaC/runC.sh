DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && sh EMStart.sh"'
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && sh EAllStart.sh"'
