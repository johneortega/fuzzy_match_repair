<?xml version="1.0" encoding="iso-8859-1"?>
<tagger name="spanish">
<tagset>
 <!-- <def-label name="PARAPR" closed="true">
    <tags-item lemma="para" tags="pr"/>
  </def-label> -->
  <def-label name="PARAVBPRI" closed="true">
    <tags-item lemma="parar" tags="vblex.pri.p3.sg"/>
  </def-label>
  <def-label name="PARAVBIMP" closed="true">
    <tags-item lemma="parar" tags="vblex.imp.p2.sg"/>
  </def-label>
  <def-label name="QUECNJ" closed="true">
    <tags-item lemma="que" tags="cnjsub"/>
  </def-label>
  <def-label name="QUEREL" closed="true">
    <tags-item lemma="que" tags="rel.*"/>
  </def-label>
<!--  <def-label name="COMOPR" closed="true">
    <tags-item lemma="como" tags="pr"/>
  </def-label>
  <def-label name="COMOREL" closed="true">
    <tags-item lemma="como" tags="rel.adv"/>
  </def-label>
  <def-label name="COMOVB" closed="true">
    <tags-item lemma="comer" tags="vblex.pri.p1.sg"/>
  </def-label> -->
  <def-label name="MASADV" closed="true">
    <tags-item lemma="m?s" tags="adv"/>
    <tags-item lemma="menos" tags="adv"/>
  </def-label>
  <def-label name="MASADJ" closed="true">
    <tags-item lemma="m?s" tags="adj.*"/>
    <tags-item lemma="menos" tags="adj.*"/>
  </def-label>
  <def-label name="MASNP" closed="true">
    <tags-item lemma="M?s" tags="np.ant.*"/>
  </def-label>
  <def-label name="ALGOADV" closed="true">
    <tags-item lemma="algo" tags="adv"/>
  </def-label>
  <def-label name="ACRONIMOM">
    <tags-item tags="n.acr.m.*"/>
  </def-label>
  <def-label name="ACRONIMOF">
    <tags-item tags="n.acr.f.*"/>
  </def-label>
  <def-label name="ACRONIMOMF">
    <tags-item tags="n.acr.mf.*"/>
  </def-label>
  <def-label name="NOMM">
    <tags-item tags="n.m.*"/>
  </def-label>
  <def-label name="NOMF">
    <tags-item tags="n.f.*"/>
  </def-label>
  <def-label name="NOMMF">
    <tags-item tags="n.mf.*"/>
  </def-label>
  <def-label name="INTNOM" closed="true">
    <tags-item tags="prn.itg.*"/>
  </def-label> 
  <def-label name="INTADV" closed="true">
    <tags-item tags="adv.itg"/>
  </def-label>
  <def-label name="PREADV" closed="true">
    <tags-item tags="preadv"/>
  </def-label>
  <def-label name="ADV">
    <tags-item tags="adv"/>
  </def-label>
  <def-label name="CNJSUBS" closed="true">
    <tags-item tags="cnjsub"/>
  </def-label>
  <def-label name="CNJCOORD" closed="true">
    <tags-item tags="cnjcoo"/>
  </def-label>
  <def-label name="CNJADV">
    <tags-item tags="cnjadv"/>
  </def-label>
  <def-label name="DETNT" closed="true">
    <tags-item tags="det.def.nt.sg"/>
  </def-label> 
<!--  <def-label name="DET" closed="true">
    <tags-item tags="det.*"/>
  </def-label> -->
<def-label name="DETQNTORD" closed="true"><!--aquests es comporten diferent dels altres dets -->
    <tags-item tags="det.qnt.*"/>
    <tags-item tags="det.ord.*"/>
  </def-label>
 <def-label name="DETM" closed="true">
    <tags-item tags="det.*.m.*"/>
  </def-label>
  <def-label name="DETF" closed="true">
    <tags-item tags="det.*.f.*"/>
  </def-label>
  <def-label name="DETMF" closed="true">
    <tags-item tags="det.*.mf.*"/>
  </def-label>
  <def-label name="INTERJ">
    <tags-item tags="ij"/>
  </def-label> 
  <def-label name="ANTROPONIM">
    <tags-item tags="np.ant.*"/>
    <tags-item tags="np.cog.*"/>
  </def-label>
  <def-label name="TOPONIM">
    <tags-item tags="np.loc.*"/>
  </def-label>
  <def-label name="NPALTRES">
    <tags-item tags="np.al.*"/>
  </def-label>
  <def-label name="NUM" closed="true">
    <tags-item tags="num.*"/>
    <tags-item tags="num"/>
  </def-label>
  <def-label name="WEB" closed="true">
    <tags-item tags="web"/>
  </def-label>
  <def-label name="PREDETNT" closed="true">
    <tags-item tags="predet.nt.*"/>
  </def-label> 
  <def-label name="PREDET" closed="true">
    <tags-item tags="predet.*"/>
  </def-label>
  <def-label name="PREP" closed="true">
    <tags-item tags="pr"/>
  </def-label>
  <def-label name="PRNTNNT" closed="true">
    <tags-item tags="prn.tn.nt"/>
  </def-label>
 <def-label name="PRNTNREF" closed="true"><!--s?lo el pronombre 's?' -->
    <tags-item tags="prn.tn.ref.p3.mf.sp"/>
  </def-label>
  <def-label name="PRNTN" closed="true">
    <tags-item tags="prn.tn.*"/>
  </def-label>
  <def-label name="PRNPOS" closed="true">
    <tags-item tags="prn.pos.*"/>
  </def-label>
  <def-label name="PRNENCREF" closed="true">
    <tags-item tags="prn.enc.ref.*"/>
  </def-label>
  <def-label name="PRNPROREF" closed="true">
    <tags-item tags="prn.pro.ref.*"/>
  </def-label>
  <def-label name="PRNENC" closed="true">
    <tags-item tags="prn.enc.*"/>
  </def-label>
  <def-label name="PRNPRO" closed="true">
    <tags-item tags="prn.pro.*"/>
  </def-label>
  <def-label name="VLEXINF">
    <tags-item tags="vblex.inf"/>
    <tags-item tags="vblex.pron.inf"/>
  </def-label>
  <def-label name="VLEXGER">
    <tags-item tags="vblex.ger"/>
    <tags-item tags="vblex.pron.ger"/>
  </def-label>
  <def-label name="VLEXPARTPI">
    <tags-item tags="vblex.pp.*"/>
    <tags-item tags="vblex.pron.pp.*"/>
  </def-label>
  <def-label name="VLEXPFCI">
    <tags-item tags="vblex.pri.*"/>
    <tags-item tags="vblex.fti.*"/>
    <tags-item tags="vblex.cni.*"/>
    <tags-item tags="vblex.pron.pri.*"/>
    <tags-item tags="vblex.pron.fti.*"/>
    <tags-item tags="vblex.pron.cni.*"/>
  </def-label>
  <def-label name="VLEXIPI">
    <tags-item tags="vblex.pii.*"/>
    <tags-item tags="vblex.ifi.*"/>
    <tags-item tags="vblex.pron.pii.*"/>
    <tags-item tags="vblex.pron.ifi.*"/>
  </def-label>
  <def-label name="VLEXSUBJ">
    <tags-item tags="vblex.prs.*"/>
    <tags-item tags="vblex.pis.*"/>
    <tags-item tags="vblex.fts.*"/>
    <tags-item tags="vblex.pron.prs.*"/>
    <tags-item tags="vblex.pron.pis.*"/>
    <tags-item tags="vblex.pron.fts.*"/>
  </def-label>
  <def-label name="VLEXIMP">
    <tags-item tags="vblex.imp.*"/>
    <tags-item tags="vblex.pron.imp.*"/>
  </def-label>
  <def-label name="VSERINF" closed="true">
    <tags-item tags="vbser.inf"/>
  </def-label>
  <def-label name="VSERGER" closed="true">
    <tags-item tags="vbser.ger"/>
  </def-label>
  <def-label name="VSERPARTPI" closed="true">
    <tags-item tags="vbser.pp.*"/>
  </def-label>
  <def-label name="VSERPFCI" closed="true">
    <tags-item tags="vbser.pri.*"/>
    <tags-item tags="vbser.fti.*"/>
    <tags-item tags="vbser.cni.*"/>
  </def-label>
  <def-label name="VSERIPI" closed="true">
    <tags-item tags="vbser.pii.*"/>
    <tags-item tags="vbser.ifi.*"/>
  </def-label>
  <def-label name="VSERSUBJ" closed="true">
    <tags-item tags="vbser.prs.*"/>
    <tags-item tags="vbser.pis.*"/>
    <tags-item tags="vbser.fts.*"/>
  </def-label>
  <def-label name="VSERIMP" closed="true">
    <tags-item tags="vbser.imp.*"/>
  </def-label>
  <def-label name="VHABERINF" closed="true">
    <tags-item tags="vbhaver.inf"/>
  </def-label>
  <def-label name="VHABERGER" closed="true">
    <tags-item tags="vbhaver.ger"/>
  </def-label>
  <def-label name="VHABERPARTPI" closed="true">
    <tags-item tags="vbhaver.pp.*"/>
  </def-label>
  <def-label name="VHABERPFCI" closed="true">
    <tags-item tags="vbhaver.pri.*"/>
    <tags-item tags="vbhaver.fti.*"/>
    <tags-item tags="vbhaver.cni.*"/>
  </def-label>
  <def-label name="VHABERIPI" closed="true">
    <tags-item tags="vbhaver.pii.*"/>
    <tags-item tags="vbhaver.ifi.*"/>
  </def-label>
  <def-label name="VHABERSUBJ" closed="true">
    <tags-item tags="vbhaver.prs.*"/>
    <tags-item tags="vbhaver.pis.*"/>
    <tags-item tags="vbhaver.fts.*"/>
  </def-label>
  <def-label name="VMODALINF" closed="true">
    <tags-item tags="vbmod.inf"/>
  </def-label>
  <def-label name="VMODALGER" closed="true">
    <tags-item tags="vbmod.ger"/>
  </def-label>
  <def-label name="VMODALPARTPI" closed="true">
    <tags-item tags="vbmod.pp.*"/>
  </def-label>
  <def-label name="VMODALPFCI" closed="true">
    <tags-item tags="vbmod.pri.*"/>
    <tags-item tags="vbmod.fti.*"/>
    <tags-item tags="vbmod.cni.*"/>
  </def-label>
  <def-label name="VMODALIPI" closed="true">
    <tags-item tags="vbmod.pii.*"/>
    <tags-item tags="vbmod.ifi.*"/>
  </def-label>
  <def-label name="VMODALSUBJ" closed="true">
    <tags-item tags="vbmod.prs.*"/>
    <tags-item tags="vbmod.pis.*"/>
    <tags-item tags="vbmod.fts.*"/>
  </def-label>
  <def-label name="VMODALIMP" closed="true">
    <tags-item tags="vbmod.imp.*"/>
  </def-label> 
 <def-label name="ADJF">
   <tags-item tags="adj.f.*"/>
   <tags-item tags="adj.ind.f.*"/>
   <tags-item tags="adj.sup.f.*"/>
 </def-label>
 <def-label name="ADJM">
   <tags-item tags="adj.m.*"/>
   <tags-item tags="adj.ind.m.*"/>
   <tags-item tags="adj.sup.m.*"/>
 </def-label>
 <def-label name="ADJMF">
   <tags-item tags="adj.mf.*"/>
   <tags-item tags="adj.ind.mf.*"/>
   <tags-item tags="adj.sup.mf.*"/>
 </def-label> 
 <def-label name="ADJINT" closed="true">
    <tags-item tags="adj.itg.*"/>
  </def-label>
 <def-label name="ADJPOS" closed="true">
    <tags-item tags="adj.pos.*"/>
  </def-label>
  <def-label name="RELN" closed="true">
    <tags-item tags="rel.an.*"/>
    <tags-item tags="rel.nn.*"/>
  </def-label>
  <def-label name="RELA" closed="true">
    <tags-item tags="rel.aa.*"/>
  </def-label>
  <def-label name="RELADV" closed="true">
    <tags-item tags="rel.adv"/>
  </def-label>
 <def-label name="GUIO" closed="true">
    <tags-item tags="guio"/>
  </def-label>  
  <def-label name="APOS" closed="true">
    <tags-item tags="apos"/>
  </def-label> 
  <def-mult name="PREPDET" closed="true">
    <sequence>
      <label-item label="PREP"/>
      <tags-item tags="det.def.m.sg"/>
    </sequence>
  </def-mult>
  <def-mult name="PRCNJ" closed="true">
    <sequence>
      <label-item label="PREP"/>
      <label-item label="QUECNJ"/>
    </sequence>
    <sequence>
      <label-item label="PREP"/>
      <label-item label="CNJSUBS"/>
    </sequence>
  </def-mult>
  <def-mult name="PRREL" closed="true">
    <sequence>
      <label-item label="PREP"/>
      <label-item label="QUEREL"/>
    </sequence>
    <sequence>
      <label-item label="PREP"/>
      <label-item label="RELN"/>
    </sequence>
  </def-mult>
  <def-mult name="INFLEXPRNENC">
    <sequence>
      <label-item label="VLEXINF"/>
      <label-item label="PRNENC"/>
   </sequence>
    <sequence>
      <label-item label="VLEXINF"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
   </sequence>
  </def-mult>
  <def-mult name="GERLEXPRNENC">
    <sequence>
      <label-item label="VLEXGER"/>
      <label-item label="PRNENC"/>
   </sequence>
    <sequence>
      <label-item label="VLEXGER"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
   </sequence>
  </def-mult>
  <def-mult name="IMPLEXPRNENC">
    <sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="PRNENC"/>
   </sequence>
    <sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
   </sequence>
  </def-mult>
  <def-mult name="INFSERPRNENC" closed="true">
    <sequence>
      <label-item label="VSERINF"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VSERINF"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="GERSERPRNENC" closed="true">
    <sequence>
      <label-item label="VSERGER"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VSERGER"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="IMPSERPRNENC" closed="true">
    <sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="INFHABERPRNENC" closed="true">
    <sequence>
      <label-item label="VHABERINF"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VHABERINF"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="GERHABERPRNENC" closed="true">
    <sequence>
      <label-item label="VHABERGER"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VHABERGER"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="INFMODPRNENC" closed="true">
    <sequence>
      <label-item label="VMODALINF"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VMODALINF"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="GERMODPRNENC" closed="true">
    <sequence>
      <label-item label="VMODALGER"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VMODALGER"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
  <def-mult name="IMPMODPRNENC" closed="true">
    <sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PRNENC"/>
    </sequence>
    <sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PRNENC"/>
      <label-item label="PRNENC"/>
    </sequence>
  </def-mult>
</tagset>

  <forbid>
    <label-sequence>
      <label-item label="ALGOADV"/>
      <label-item label="QUECNJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ALGOADV"/>
      <label-item label="PREADV"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ALGOADV"/>
      <label-item label="ADV"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="PREP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="PREPDET"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
 <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
 <label-sequence><!-- 'si guanya' -->
      <label-item label="CNJADV"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>

    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="RELA"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <!--<label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="VSERIMP"/>
    </label-sequence> -->
    <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJADV"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
   <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
   <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
     <label-sequence>
      <label-item label="RELA"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
  <!--  <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="VMODALIMP"/>
    </label-sequence> -->
     <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
     <label-sequence>
      <label-item label="CNJADV"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
     <label-sequence>
      <label-item label="CNJADV"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="VMODALIMP"/>
    </label-sequence> 
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="VMODALIMP"/>
    </label-sequence> 
     <label-sequence>
      <label-item label="RELA"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
   <!-- <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence> -->
     <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence> 
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence> 
     <label-sequence>
      <label-item label="RELA"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
  <!--  <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence> -->
     <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
     <label-sequence>
      <label-item label="RELA"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
   <!-- <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence> -->
      <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
   <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence> 
   <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence> 
      <label-sequence>
      <label-item label="RELA"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VLEXGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VSERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VHABERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="VMODALGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VLEXGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VSERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VHABERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="VMODALGER"/>
    </label-sequence>
<label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VLEXGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VSERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VHABERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="VMODALGER"/>
    </label-sequence>



    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VLEXGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VSERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VHABERGER"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALGER"/>
    </label-sequence>



    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="VMODALINF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="NOMM"/>
    </label-sequence>
 <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="NOMF"/>
    </label-sequence>
 <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="NUM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="PRNPRO"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="PRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="PRNPRO"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="PRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="PRNPRO"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="PRNENC"/>
    </label-sequence>
  
  <label-sequence>
      <label-item label="DETM"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence> 
      <label-item label="DETM"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence> 
      <label-item label="DETF"/>
      <label-item label="DETF"/>
    </label-sequence> 
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="DETMF"/>
    </label-sequence> 
   <label-sequence> 
      <label-item label="DETMF"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence> 
      <label-item label="DETMF"/>
      <label-item label="DETF"/>
    </label-sequence> 
    <label-sequence> 
      <label-item label="DETMF"/>
      <label-item label="DETMF"/>
    </label-sequence>

    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETM"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETF"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="DETMF"/>
      <label-item label="SENT"/>
    </label-sequence>  
    <label-sequence>
      <label-item label="DETNT"/>
      <label-item label="SENT"/>
    </label-sequence>  
    <label-sequence>
      <label-item label="PREP"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREDET"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREDETNT"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="RELN"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="RELAA"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="RELADV"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJCOORD"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJADV"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PREPDET"/>
      <label-item label="SENT"/>
    </label-sequence>
    <label-sequence><!--aunque s? piensa -->
      <label-item label="CNJADV"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--pero s? lleg? -->
      <label-item label="CNJCOORD"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--ello s? significa -->
      <label-item label="PRNTNNT"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--yo s? quiero -->
      <label-item label="PRNTN"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPOS"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ADV"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--hablando s? se entiende la gente -->
      <label-item label="VLEXGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>


    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOM"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOF"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ACRONIMOMF"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPOS"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPOS"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPOS"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="IMPMODPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="IMPSERPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="ACRONIMOM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="ACRONIMOF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ANTROPONIM"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="TOPONIM"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NPALTRES"/>
      <label-item label="ACRONIMOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJADV"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="IMPLEXPRNENC"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VLEXPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VLEXIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VLEXSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VSERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VSERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VSERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VHABERPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VHABERIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VHABERSUBJ"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VMODALPFCI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VMODALIPI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="IMPLEXPRNENC"/>
      <label-item label="VMODALSUBJ"/>
    </label-sequence>  
    <label-sequence>
      <label-item label="VLEXINF"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXGER"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPARTPI"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPFCI"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIPI"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXSUBJ"/>
      <label-item label="PRREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="PRREL"/>
    </label-sequence> 
    <label-sequence>
      <label-item label="VMODALINF"/><!--Afegit 17/01/2006 fins a VMODALIMP-->
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="ADJPOS"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="DETNT"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="DETM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="DETF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="DETMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="MASADJ"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="ADJM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="ADJF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="ADJMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="ADJPOS"/>
    </label-sequence><label-sequence><!--Afegides les 3 seg?ents 30/11/05 MG -->
      <label-item label="INTNOM"/>
      <label-item label="NOMF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="INTNOM"/>
      <label-item label="NOMM"/>
    </label-sequence>
    <label-sequence>
      <label-item label="INTNOM"/>
      <label-item label="NOMMF"/>
    </label-sequence>
    <label-sequence><!--13/01/06 -->
      <label-item label="MOLTADV"/>
      <label-item label="INTERJ"/>
    </label-sequence>
    <label-sequence><!--01/02/06 (no canvia res)-->
      <label-item label="ADV"/>
      <label-item label="INTERJ"/>
    </label-sequence>
     <label-sequence><!--11/10/06 -->
      <label-item label="VMODALPARTPI"/>
      <label-item label="PREP"/>
    </label-sequence>
    <label-sequence><!--28/11/06 tots els forbids amb querel: prova-->
      <label-item label="VOLERMOD"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VOLERLEX"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VLEXINF"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VLEXGER"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPFCI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIPI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXSUBJ"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERINF"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VSERGER"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VSERPFCI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIPI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERSUBJ"/>
      <label-item label="QUEREL"/>
    </label-sequence>
     <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="QUEREL"/>
    </label-sequence>
 <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="QUEREL"/>
    </label-sequence>
<label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="QUEREL"/>
    </label-sequence>
<label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
<label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="QUEREL"/>
    </label-sequence>
    <label-sequence><!--ella piensa -->
      <label-item label="PRNTN"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNTN"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNTN"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence><!--siempre/no piensa -->
      <label-item label="ADV"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ADV"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ADV"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>

    <label-sequence><!--se incluye -->
      <label-item label="PRNPROREF"/>
      <label-item label="VLEXIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPROREF"/>
      <label-item label="VSERIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPROREF"/>
      <label-item label="VMODALIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPROREF"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence><!--aunque s? piensa -->
      <label-item label="CNJADV"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--pero s? lleg? -->
      <label-item label="CNJCOORD"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="CNJSUBS"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUECNJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="QUEREL"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--ello s? significa -->
      <label-item label="PRNTNNT"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--yo s? quiero -->
      <label-item label="PRNTN"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="PRNPOS"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMM"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="NOMMF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="ADV"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence><!--hablando s? se entiende la gente -->
      <label-item label="VLEXGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VLEXIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PRNTNREF"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPFCI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPFCI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIPI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERSUBJ"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERSUBJ"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERIMP"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERINF"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERGER"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VSERPARTPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>

    <label-sequence>
      <label-item label="VHABERPFCI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPFCI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIPI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERSUBJ"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERSUBJ"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIMP"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERIMP"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERINF"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERGER"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VHABERPARTPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>

    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPFCI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIPI"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALSUBJ"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALIMP"/>
      <label-item label="PARAVBIMP"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALINF"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALGER"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>
    <label-sequence>
      <label-item label="VMODALPARTPI"/>
      <label-item label="PARAVBPRI"/>
    </label-sequence>



  </forbid>

  <enforce-rules>
    <enforce-after label="PRNPRO">
      <label-set>
        <label-item label="PRNPRO"/>
        <label-item label="VLEXPFCI"/>
        <label-item label="VLEXIPI"/>
        <label-item label="VLEXSUBJ"/>
        <label-item label="VSERPFCI"/>
        <label-item label="VSERIPI"/>
        <label-item label="VSERSUBJ"/>
        <label-item label="VHABERPFCI"/>
        <label-item label="VHABERIPI"/>
        <label-item label="VHABERSUBJ"/>
        <label-item label="VMODALPFCI"/>
        <label-item label="VMODALIPI"/>
        <label-item label="VMODALSUBJ"/>
      </label-set>
    </enforce-after>

 <enforce-after label="PARAVBPRI">
      <label-set>
        <label-item label="SENT"/>
      </label-set>
 </enforce-after>
<enforce-after label="PARAVBIMP">
      <label-set>
        <label-item label="SENT"/>
      </label-set>
 </enforce-after>
  </enforce-rules>

  <preferences>
   <prefer tags="vblex.pii.p3.sg"/>
   <prefer tags="vbser.pii.p3.sg"/>
   <prefer tags="vbhaver.pii.p3.sg"/>
   <prefer tags="vbmod.pii.p3.sg"/>
   <prefer tags="vblex.prs.p3.sg"/>
   <prefer tags="vbser.prs.p3.sg"/>
   <prefer tags="vbhaver.prs.p3.sg"/>
   <prefer tags="vbmod.prs.p3.sg"/>
   <prefer tags="vblex.cni.p3.sg"/>
   <prefer tags="vbser.cni.p3.sg"/>
   <prefer tags="vbhaver.cni.p3.sg"/>
   <prefer tags="vbmod.cni.p3.sg"/>
   <prefer tags="vblex.pis.p3.sg"/>
   <prefer tags="vbser.pis.p3.sg"/>
   <prefer tags="vbhaver.pis.p3.sg"/>
   <prefer tags="vbmod.pis.p3.sg"/>
  </preferences>
</tagger>
