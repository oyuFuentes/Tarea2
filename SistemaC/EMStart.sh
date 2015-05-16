rmic EventManager
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && rmiregistry"' 
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java EventManager"' 

