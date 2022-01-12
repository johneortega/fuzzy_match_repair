###############################################################################
## Makefile for French--Spanish
###############################################################################

VERSION=0.9.2
LANG1=fr
LANG2=es
PREFIX1=$(LANG1)-$(LANG2)
PREFIX2=$(LANG2)-$(LANG1)

BASENAME=apertium-$(PREFIX1)

VAR1=std
VAR2=eco
PREFVAR1=#dejar en blanco
PREFVAR2=eco-
PREFVAR2x=eco

TARGETS_COMMON = \
	$(PREFIX1).autopgen.bin \
	$(PREFIX1).$(PREFVAR2x).autolex.bin \
	$(PREFIX1).t1x.bin \
	$(PREFIX1).t2x.bin \
	$(PREFIX1).t3x.bin \
	$(PREFIX2).autopgen.bin \
	$(PREFIX2).$(PREFVAR2x).autolex.bin \
	$(PREFIX2).t1x.bin \
	$(PREFIX2).t2x.bin \
	$(PREFIX2).t3x.bin \
	$(PREFVAR1)$(PREFIX1).autobil.bin \
	$(PREFVAR1)$(PREFIX1).autogen.bin \
	$(PREFVAR1)$(PREFIX1).autolex.bin \
	$(PREFVAR1)$(PREFIX1).automorf.bin \
	$(PREFVAR1)$(PREFIX2).autobil.bin \
	$(PREFVAR1)$(PREFIX2).autogen.bin \
	$(PREFVAR1)$(PREFIX2).autolex.bin \
	$(PREFVAR1)$(PREFIX2).automorf.bin \
	$(PREFVAR2)$(PREFIX1).autobil.bin \
	$(PREFVAR2)$(PREFIX1).autogen.bin \
	$(PREFVAR2)$(PREFIX1).automorf.bin \
	$(PREFVAR2)$(PREFIX2).autobil.bin \
	$(PREFVAR2)$(PREFIX2).autogen.bin \
	$(PREFVAR2)$(PREFIX2).automorf.bin

# Use this goal for creating .deps, otherwise make -j2 will give problems:
.deps/.d:
	if [ ! -d .deps ]; then mkdir .deps; fi
	touch $@

# Prevent the .d file from being removed automatically:
.PRECIOUS: .deps/.d


###############################################################################
## Transducers, disambiguation rules, transfer
###############################################################################

.deps/$(BASENAME).$(LANG1).dix: $(BASENAME).$(LANG1).metadix
	@xsltproc buscaPar.xsl $< |gawk '{gsub("&", "&amp;"); print;}' | uniq > $$gen.xsl
	@xsltproc $$gen.xsl $< | gawk '{gsub("&", "&amp;"); print;}' >$@
	@rm $$gen.xsl


.deps/$(PREFVAR1)$(LANG1).dix: .deps/$(BASENAME).$(LANG1).dix
	xsltproc --stringparam alt $(VAR1) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}' >$@

.deps/$(PREFVAR2)$(LANG1).dix: .deps/$(BASENAME).$(LANG1).dix
	xsltproc --stringparam alt $(VAR2) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}' >$@

.deps/$(PREFVAR1)$(LANG2).dix: $(BASENAME).$(LANG2).dix .deps/.d
	xsltproc --stringparam alt $(VAR1) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}' >$@

.deps/$(PREFVAR2)$(LANG2).dix: $(BASENAME).$(LANG2).dix .deps/.d
	xsltproc --stringparam alt $(VAR2) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}'>$@

.deps/common-$(PREFIX1).dix: $(BASENAME).$(PREFIX1).dix .deps/.d
	xsltproc translate-to-default-equivalent.xsl $<  | gawk '{gsub("&", "&amp;"); print;}' >$@

.deps/$(PREFVAR1)$(PREFIX1).dix: .deps/common-$(PREFIX1).dix
	xsltproc --stringparam alt $(VAR1) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}' >$@

.deps/$(PREFVAR2)$(PREFIX1).dix: .deps/common-$(PREFIX1).dix
	xsltproc --stringparam alt $(VAR2) alt.xsl $< | gawk '{gsub("&", "&amp;"); print;}'>$@


$(PREFVAR1)$(PREFIX1).automorf.bin: .deps/$(PREFVAR1)$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@ $(BASENAME).$(LANG1).acx

$(PREFVAR1)$(PREFIX1).autobil.bin: .deps/$(PREFVAR1)$(PREFIX1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFIX1).autolex.bin: $(BASENAME).$(PREFIX1).lrx
	lrx-comp $< $@

$(PREFVAR1)$(PREFIX1).autogen.bin: .deps/$(PREFVAR1)$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@  $(BASENAME).$(LANG2).acx

$(PREFVAR1)$(PREFIX1).autopgen.bin: $(BASENAME).post-$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFVAR2)$(PREFIX1).automorf.bin: .deps/$(PREFVAR2)$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@  $(BASENAME).$(LANG1).acx

$(PREFVAR2)$(PREFIX1).autobil.bin: .deps/$(PREFVAR2)$(PREFIX1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFIX1).$(PREFVAR2x).autolex.bin: $(BASENAME).$(PREFIX1).$(PREFVAR2x).lrx
	lrx-comp $< $@

$(PREFVAR2)$(PREFIX1).autogen.bin: .deps/$(PREFVAR2)$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@ $(BASENAME).$(LANG1).acx

$(PREFVAR2)$(PREFIX1).autopgen.bin: $(BASENAME).post-$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFVAR1)$(PREFIX2).automorf.bin: .deps/$(PREFVAR1)$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@ $(BASENAME).$(LANG2).acx

$(PREFVAR1)$(PREFIX2).autobil.bin: .deps/$(PREFVAR1)$(PREFIX1).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@

$(PREFIX2).autolex.bin: $(BASENAME).$(PREFIX2).lrx
	lrx-comp $< $@

$(PREFVAR1)$(PREFIX2).autogen.bin: .deps/$(PREFVAR1)$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@ $(BASENAME).$(LANG2).acx

$(PREFVAR1)$(PREFIX2).autopgen.bin: $(BASENAME).post-$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFVAR2)$(PREFIX2).automorf.bin: .deps/$(PREFVAR2)$(LANG2).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFVAR2)$(PREFIX2).autobil.bin: .deps/$(PREFVAR2)$(PREFIX1).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@

$(PREFIX2).$(PREFVAR2x).autolex.bin: $(BASENAME).$(PREFIX2).$(PREFVAR2x).lrx
	lrx-comp $< $@

$(PREFVAR2)$(PREFIX2).autogen.bin: .deps/$(PREFVAR2)$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp rl $< $@

$(PREFVAR2)$(PREFIX2).autopgen.bin: $(BASENAME).post-$(LANG1).dix
	apertium-validate-dictionary $<
	lt-comp lr $< $@

$(PREFVAR1)$(PREFIX1).t1x.bin: $(BASENAME).$(PREFIX1).t1x
	apertium-validate-transfer $<
	apertium-preprocess-transfer $< $@

$(PREFVAR1)$(PREFIX1).t2x.bin: $(BASENAME).$(PREFIX1).t2x
	apertium-validate-interchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR1)$(PREFIX1).t3x.bin: $(BASENAME).$(PREFIX1).t3x
	apertium-validate-postchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX1).t1x.bin: $(BASENAME).$(PREFIX1).t1x
	apertium-validate-transfer $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX1).t2x.bin: $(BASENAME).$(PREFIX1).t2x
	apertium-validate-interchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX1).t3x.bin: $(BASENAME).$(PREFIX1).t3x
	apertium-validate-postchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR1)$(PREFIX2).t1x.bin: $(BASENAME).$(PREFIX2).t1x
	apertium-validate-transfer $<
	apertium-preprocess-transfer $< $@

$(PREFVAR1)$(PREFIX2).t2x.bin: $(BASENAME).$(PREFIX2).t2x
	apertium-validate-interchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR1)$(PREFIX2).t3x.bin: $(BASENAME).$(PREFIX2).t3x
	apertium-validate-postchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX2).t1x.bin: $(BASENAME).$(PREFIX2).t1x
	apertium-validate-transfer $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX2).t2x.bin: $(BASENAME).$(PREFIX2).t2x
	apertium-validate-interchunk $<
	apertium-preprocess-transfer $< $@

$(PREFVAR2)$(PREFIX2).t3x.bin: $(BASENAME).$(PREFIX2).t3x
	apertium-validate-postchunk $<
	apertium-preprocess-transfer $< $@



###############################################################################
## Distribution
###############################################################################

EXTRA_DIST=$(BASENAME).$(PREFIX1).dix \
           $(BASENAME).post-$(LANG1).dix $(BASENAME).post-$(LANG2).dix \
           $(BASENAME).$(LANG2).dix $(BASENAME).$(LANG1).tsx \
           $(BASENAME).$(LANG2).tsx  \
           $(PREFIX1).prob $(PREFIX2).prob \
           buscaPar.xsl principal.xsl apertium-fr-es.fr.metadix \
           $(PREFIX1)-supervised.make $(PREFIX1)-unsupervised.make \
           $(PREFIX2)-supervised.make $(PREFIX2)-unsupervised.make \
           $(LANG1)-tagger-data/README $(LANG2)-tagger-data/README \
           $(BASENAME).$(PREFIX1).t1x $(BASENAME).$(PREFIX1).t2x \
           $(BASENAME).$(PREFIX1).t3x $(BASENAME).$(PREFIX2).t1x \
           $(BASENAME).$(PREFIX2).t2x $(BASENAME).$(PREFIX2).t3x \
           modes.xml  $(BASENAME).$(LANG1).acx alt.xsl translate-to-default-equivalent.xsl \
	   $(BASENAME).$(LANG2).acx


###############################################################################
## Installation
###############################################################################

apertium_fr_esdir=$(prefix)/share/apertium/apertium-$(PREFIX1)/
apertium_modesdir=$(prefix)/share/apertium/modes/

modes/$(PREFIX1).mode: modes.xml
	apertium-validate-modes modes.xml
	apertium-gen-modes modes.xml
	cp *.mode modes/

# All modes are created by the same goal, listing several will just give problems with make -j2
noinst_DATA=modes/$(PREFIX1).mode


# Files which should be installed to $(prefix)/share/apertium/$(BASENAME)/:
apertium_fr_es_DATA=$(TARGETS_COMMON) \
	$(PREFIX1).prob \
	$(PREFIX2).prob \
	$(BASENAME).$(PREFIX1).t1x \
	$(BASENAME).$(PREFIX1).t2x \
	$(BASENAME).$(PREFIX1).t3x \
	$(BASENAME).$(PREFIX2).t1x \
	$(BASENAME).$(PREFIX2).t2x \
	$(BASENAME).$(PREFIX2).t3x

install-data-local:
	mv modes modes.bak
	apertium-gen-modes modes.xml apertium-$(PREFIX1)
	rm -rf modes
	mv modes.bak modes
	test -d $(DESTDIR)$(apertium_modesdir) || mkdir $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFVAR1)$(PREFIX1).mode $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFVAR1)$(PREFIX2).mode $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFVAR2)$(PREFIX1).mode $(DESTDIR)$(apertium_modesdir)
	$(INSTALL_DATA) $(PREFVAR2)$(PREFIX2).mode $(DESTDIR)$(apertium_modesdir)
	rm $(PREFVAR1)$(PREFIX1).mode $(PREFVAR1)$(PREFIX2).mode $(PREFVAR2)$(PREFIX1).mode $(PREFVAR2)$(PREFIX2).mode 


###############################################################################
## Cleanup
###############################################################################

CLEANFILES = $(TARGETS_COMMON)
clean-local: 
	-rm -rf .deps modes

###############################################################################
## Test
###############################################################################

test: all
	echo "Donde es la estación?" | apertium -d . es-fr | tee .test-mt
	@echo "Où est la gare?" > .test-ref
	@diff .test-ref .test-mt
	echo "La plume de ma tante" | apertium -d . fr-es | tee .test-mt
	@echo "La pluma de mi tía" > .test-ref
	@diff .test-ref .test-mt
	@rm .test-ref .test-mt
