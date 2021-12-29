#!/usr/bin/python
import sys
import xml.etree.ElementTree as ET
if len(sys.argv) < 2:
	print "please enter a language (es|en)"
	sys.exit(0)
if(len(sys.argv[1]) < 1 ):
	print "please enter a language (es|en)"
	sys.exit(0)
e = ET.parse('en_es_2016.tmx').getroot()
for tuv in e.iter('tuv'):
	if(str(tuv.attrib['{http://www.w3.org/XML/1998/namespace}lang'])=='EN-GB' and sys.argv[1].upper()=='EN'):
		print tuv.find('seg').text.encode('utf-8')
	elif(str(tuv.attrib['{http://www.w3.org/XML/1998/namespace}lang'])=='ES-ES' and sys.argv[1].upper()=='ES'):
		print tuv.find('seg').text.encode('utf-8')
