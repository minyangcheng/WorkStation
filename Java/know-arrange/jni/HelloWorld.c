
#include "HelloWorld.h"
#include <stdio.h>
 
JNIEXPORT void JNICALL Java_HelloWorld_print
  (JNIEnv *, jobject) {
	printf("Hello World!");
 

