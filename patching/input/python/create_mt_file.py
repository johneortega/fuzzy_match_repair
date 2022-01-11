#!/usr/bin/python env
import sys
a='a.txt'
m='m.txt'
n='n.txt'

amap={}
acnt=0
nmap={}
ncnt=0
mmap={}
mcnt=0
with open(a,'rb') as af:
    for line in af:
        translation=line.strip()
        amap[acnt]=translation
        acnt+=1
with open(m,'rb') as mf:
    for line in mf:
        translation=line.strip()
        mmap[mcnt]=translation
        mcnt+=1
with open(n,'rb') as nf:
    for line in nf:
        translation=line.strip()
        nmap[ncnt]=translation
        ncnt+=1
cnt=0
with open('label.txt','rb') as l:
    for line in l:
        labelint=line.strip()
        if labelint == '0':
            print mmap[cnt]
        elif labelint == '1':
            print amap[cnt]
        elif labelint == '2':
            print nmap[cnt]
        cnt+=1
    
