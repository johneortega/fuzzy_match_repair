LANG1=es
LANG2=pt
LANG2b=pt_BR
PREFIX1=$(LANG1)-$(LANG2)
PREFIX1b=$(LANG1)-$(LANG2b)
PREFIX2=$(LANG2)-$(LANG1)

BASENAME=apertium-es-pt

TARGETS_COMMON = \
	$(PREFIX1).autobil.bin \
	$(PREFIX1).autogen.bin \
	$(PREFIX1).automorf.bin \
	$(PREFIX1).autopgen.bin \
	$(PREFIX1b).autobil.bin \
	$(PREFIX1b).autogen.bin \
	$(PREFIX1b).autopgen.bin \
	$(PREFIX1b).t1x \
	$(PREFIX1b).t1x.bin \
	$(PREFIX1).t1x \
	$(PREFIX1).t1x.bin \
	$(PREFIX2).autobil.bin \
	$(PREFIX2).autogen.bin \
	$(PREFIX2).automorf.bin \
	$(PREFIX2).autopgen.bin \
	$(PREFIX2).t1x \
	$(PREFIX2).t1x.bin

$(PREFIX1).automorf.bin: $(BASENAME).$(LANG1).dix
	apertium-validate-dictionary $(BASENAME).$(LANG1).dix
	lt-comp lr $(BASENAME).$(LANG1).dix $@

$(PREFIX1).autobil.bin: es-pt.dix
	apertium-validate-dictionary es-pt.dix
	lt-comp lr es-pt.dix $@

$(PREFIX1).autogen.bin: pt.dix
	apertium-validate-dictionary pt.dix
	lt-comp rl pt.dix $@

$(PREFIX1b).autobil.bin: es-pt_BR.dix
	apertium-validate-dictionary es-pt_BR.dix
	lt-comp lr es-pt_BR.dix $@

$(PREFIX1b).autogen.bin: pt_BR.dix
	apertium-validate-dictionary pt_BR.dix
	lt-comp rl pt_BR.dix $@

#$(PREFIX1).autopgen.bin: $(BASENAME).post-$(LANG2).dix
#	apertium-validate-dictionary $(BASENAME).post-$(LANG2).dix
#	lt-comp lr $(BASENAME).post-$(LANG2).dix $@

es-pt.autopgen.bin: post-pt.dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

es-pt_BR.autopgen.bin: post-pt_BR.dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@


$(PREFIX2).automorf.bin: $(BASENAME).$(LANG2).dix
#	apertium-validate-dictionary $(BASENAME).$(LANG2).dix
	lt-comp lr $(BASENAME).$(LANG2).dix $@

$(PREFIX2).autobil.bin: $(PREFIX1).dix
#	apertium-validate-dictionary $(BASENAME).$(PREFIX1).dix
	lt-comp rl $(PREFIX1).dix $@

$(PREFIX2).autogen.bin: $(BASENAME).$(LANG1).dix
	apertium-validate-dictionary $(BASENAME).$(LANG1).dix
	lt-comp rl $(BASENAME).$(LANG1).dix $@

$(PREFIX2).autopgen.bin: $(BASENAME).post-$(LANG1).dix
	apertium-validate-dictionary $(BASENAME).post-$(LANG1).dix
	lt-comp lr $(BASENAME).post-$(LANG1).dix $@

$(PREFIX2).t1x.bin: $(BASENAME).$(PREFIX2).t1x
	apertium-validate-transfer $(BASENAME).$(PREFIX2).t1x
	apertium-preprocess-transfer $(BASENAME).$(PREFIX2).t1x \
	                             $(PREFIX2).t1x.bin

$(PREFIX1).t1x: $(BASENAME).$(PREFIX1).t1x
	./filterrules.sh pt_PT $(BASENAME).$(PREFIX1).t1x >$@

$(PREFIX1b).t1x: $(BASENAME).$(PREFIX1).t1x
	./filterrules.sh pt_BR $(BASENAME).$(PREFIX1).t1x >$@	                      

$(PREFIX1).t1x.bin: $(PREFIX1).t1x
	apertium-validate-transfer $(PREFIX1).t1x
	apertium-preprocess-transfer $(PREFIX1).t1x $@

$(PREFIX1b).t1x.bin: $(PREFIX1b).t1x
	apertium-validate-transfer $(PREFIX1b).t1x
	apertium-preprocess-transfer $(PREFIX1b).t1x $@

$(PREFIX2).t1x: $(BASENAME).$(PREFIX2).t1x
	cp $(BASENAME).$(PREFIX2).t1x $(PREFIX2).t1x



pt.dix: $(BASENAME).$(LANG2).dix
	./filter.sh pt left $(BASENAME).$(LANG2).dix >$@

pt_BR.dix: $(BASENAME).$(LANG2).dix
	./filter.sh br left $(BASENAME).$(LANG2).dix >$@

es-pt.dix: $(BASENAME).$(PREFIX1).dix
	./filter.sh pt right $(BASENAME).$(PREFIX1).dix >$@

es-pt_BR.dix: $(BASENAME).$(PREFIX1).dix
	./filter.sh br right $(BASENAME).$(PREFIX1).dix >$@

post-pt.dix: $(BASENAME).post-pt.dix
	./filter.sh pt right $< >$@

post-pt_BR.dix: $(BASENAME).post-pt.dix
	./filter.sh br right $< >$@


#
#	MODE FILE(S)
#

modes/$(PREFIX1).mode: modes.xml
	apertium-validate-modes modes.xml
	apertium-gen-modes modes.xml
	cp *.mode modes/

##
##	FILES WHICH AREN'T BUILD BUT ARE DISTRIBUTED
##

EXTRA_DIST=$(BASENAME).$(PREFIX1).dix \
           $(BASENAME).post-$(LANG1).dix $(BASENAME).post-$(LANG2).dix \
           $(BASENAME).$(LANG2).dix $(BASENAME).$(LANG1).tsx $(BASENAME).$(LANG1).dix \
           $(BASENAME).$(LANG2).tsx $(BASENAME).$(PREFIX1).t1x \
           $(BASENAME).$(PREFIX2).t1x $(PREFIX1).prob $(PREFIX2).prob \
           $(PREFIX1)-supervised.make $(PREFIX1)-unsupervised.make \
           $(PREFIX2)-supervised.make $(PREFIX2)-unsupervised.make \
	   $(LANG2).dix $(LANG2b).dix $(PREFIX1).dix $(PREFIX1b).dix \
           filter.sh filter.xsl filterrules.sh filterrules.xsl modes.xml


apertium_es_ptdir=$(prefix)/share/apertium/apertium-$(PREFIX1)/
apertium_modesdir=$(prefix)/share/apertium/modes/


apertium_es_pt_DATA= \
	$(TARGETS_COMMON) \
	$(PREFIX1).prob \
	$(PREFIX2).prob

# All modes are created by the same goal, listing several will just give problems with make -j2
noinst_DATA=modes/$(PREFIX1).mode

install-data-local:
	mv modes modes.bak
	apertium-gen-modes modes.xml apertium-$(PREFIX1)
	rm -rf modes
	mv modes.bak modes
	test -d $(DESTDIR)$(apertium_modesdir) || mkdir $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFIX1).mode $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFIX1b).mode $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFIX2).mode $(DESTDIR)$(apertium_modesdir)
	rm -f $(PREFIX1).mode $(PREFIX1b).mode $(PREFIX2).mode


CLEANFILES = $(TARGETS_COMMON)
clean-local:
	-rm -rf modes
