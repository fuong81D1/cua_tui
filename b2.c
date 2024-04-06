#include<stdio.h>

int main(){
	enum Company { GOOGLE, FACEBOOK, XEROX, YAHOO, EBAY, MICROSOFT };
	enum Company ten1 = GOOGLE;
	enum Company ten2 = FACEBOOK;
	enum Company ten3 = XEROX;
	enum Company ten4 = YAHOO;
	enum Company ten5 = EBAY;
	enum Company ten6 = MICROSOFT;

	//printf("%d", ten);
	printf("%d\n", ten1);
	printf("%d\n", ten2);
	printf("%d\n", ten3);
	printf("%d\n", ten4);
	printf("%d\n", ten5);
	printf("%d\n", ten6);
}
