__pwd=$(cd "$(dirname "$0")"; pwd)
#echo $__pwd
__CP=`ls $__pwd/../lib/*.jar | xargs -I {} echo -n {}:`
echo classpath: $__CP
java -cp .:$__CP com.javaapi.test.boot.Application