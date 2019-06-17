import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Application {

    private static Logger logger;
    private static FileHandler fh;
    private static String fileName;

    private enum LocatorType{
        id, name, css, xpath, tag, text, partText, linkText, partialLinkText;
    }

    public static void main(String []args){

        String browserName;
        Boolean isTest;
        File f = null;

        try {
            browserName = args[0];
            fileName = args[1];

            if(fileName =="" || browserName=="")
            {

                logger.warning("Файл задания не найден (Код 0.9)");
                return;
            }
        }catch (Exception e){
            fileName = "d:\\dst\\wws\\commands\\error";
            initialLog(true);
            logger.warning("Ошибка при получении параметров (Код 1.0)");
            return;
        }

        isTest = fileName.contains("test");
        initialLog(isTest);


        logger.info("Запуск с параметрами: " + browserName + " " + fileName);
        if(!isTest){
            f = new File(fileName);
            if(!f.exists()){
                logger.warning("Файл задания не найден (Код 0.8)");
                return;
            }
        }

        // определение браузера
        WebDriver webdriver=null;

        if (browserName.equals("firefox") ){
            try {

                // Автоматическое скачивание в папку
                /*FirefoxOptions options = new FirefoxOptions();
                options.addPreference("browser.download.folderList", 2)
                        .addPreference("browser.download.dir", "d:\\dst\\wws\\downloads\\")
                        .addPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.hzn-3d-crossword;video/3gpp;video/3gpp2;application/vnd.mseq;application/vnd.3m.post-it-notes;application/vnd.3gpp.pic-bw-large;application/vnd.3gpp.pic-bw-small;application/vnd.3gpp.pic-bw-var;application/vnd.3gp2.tcap;application/x-7z-compressed;application/x-abiword;application/x-ace-compressed;application/vnd.americandynamics.acc;application/vnd.acucobol;application/vnd.acucorp;audio/adpcm;application/x-authorware-bin;application/x-athorware-map;application/x-authorware-seg;application/vnd.adobe.air-application-installer-package+zip;application/x-shockwave-flash;application/vnd.adobe.fxp;application/pdf;application/vnd.cups-ppd;application/x-director;applicaion/vnd.adobe.xdp+xml;application/vnd.adobe.xfdf;audio/x-aac;application/vnd.ahead.space;application/vnd.airzip.filesecure.azf;application/vnd.airzip.filesecure.azs;application/vnd.amazon.ebook;application/vnd.amiga.ami;applicatin/andrew-inset;application/vnd.android.package-archive;application/vnd.anser-web-certificate-issue-initiation;application/vnd.anser-web-funds-transfer-initiation;application/vnd.antix.game-component;application/vnd.apple.installe+xml;application/applixware;application/vnd.hhe.lesson-player;application/vnd.aristanetworks.swi;text/x-asm;application/atomcat+xml;application/atomsvc+xml;application/atom+xml;application/pkix-attr-cert;audio/x-aiff;video/x-msvieo;application/vnd.audiograph;image/vnd.dxf;model/vnd.dwf;text/plain-bas;application/x-bcpio;application/octet-stream;image/bmp;application/x-bittorrent;application/vnd.rim.cod;application/vnd.blueice.multipass;application/vnd.bm;application/x-sh;image/prs.btif;application/vnd.businessobjects;application/x-bzip;application/x-bzip2;application/x-csh;text/x-c;application/vnd.chemdraw+xml;text/css;chemical/x-cdx;chemical/x-cml;chemical/x-csml;application/vn.contact.cmsg;application/vnd.claymore;application/vnd.clonk.c4group;image/vnd.dvb.subtitle;application/cdmi-capability;application/cdmi-container;application/cdmi-domain;application/cdmi-object;application/cdmi-queue;applicationvnd.cluetrust.cartomobile-config;application/vnd.cluetrust.cartomobile-config-pkg;image/x-cmu-raster;model/vnd.collada+xml;text/csv;application/mac-compactpro;application/vnd.wap.wmlc;image/cgm;x-conference/x-cooltalk;image/x-cmx;application/vnd.xara;application/vnd.cosmocaller;application/x-cpio;application/vnd.crick.clicker;application/vnd.crick.clicker.keyboard;application/vnd.crick.clicker.palette;application/vnd.crick.clicker.template;application/vn.crick.clicker.wordbank;application/vnd.criticaltools.wbs+xml;application/vnd.rig.cryptonote;chemical/x-cif;chemical/x-cmdf;application/cu-seeme;application/prs.cww;text/vnd.curl;text/vnd.curl.dcurl;text/vnd.curl.mcurl;text/vnd.crl.scurl;application/vnd.curl.car;application/vnd.curl.pcurl;application/vnd.yellowriver-custom-menu;application/dssc+der;application/dssc+xml;application/x-debian-package;audio/vnd.dece.audio;image/vnd.dece.graphic;video/vnd.dec.hd;video/vnd.dece.mobile;video/vnd.uvvu.mp4;video/vnd.dece.pd;video/vnd.dece.sd;video/vnd.dece.video;application/x-dvi;application/vnd.fdsn.seed;application/x-dtbook+xml;application/x-dtbresource+xml;application/vnd.dvb.ait;applcation/vnd.dvb.service;audio/vnd.digital-winds;image/vnd.djvu;application/xml-dtd;application/vnd.dolby.mlp;application/x-doom;application/vnd.dpgraph;audio/vnd.dra;application/vnd.dreamfactory;audio/vnd.dts;audio/vnd.dts.hd;imag/vnd.dwg;application/vnd.dynageo;application/ecmascript;application/vnd.ecowin.chart;image/vnd.fujixerox.edmics-mmr;image/vnd.fujixerox.edmics-rlc;application/exi;application/vnd.proteus.magazine;application/epub+zip;message/rfc82;application/vnd.enliven;application/vnd.is-xpr;image/vnd.xiff;application/vnd.xfdl;application/emma+xml;application/vnd.ezpix-album;application/vnd.ezpix-package;image/vnd.fst;video/vnd.fvt;image/vnd.fastbidsheet;application/vn.denovo.fcselayout-link;video/x-f4v;video/x-flv;image/vnd.fpx;image/vnd.net-fpx;text/vnd.fmi.flexstor;video/x-fli;application/vnd.fluxtime.clip;application/vnd.fdf;text/x-fortran;application/vnd.mif;application/vnd.framemaker;imae/x-freehand;application/vnd.fsc.weblaunch;application/vnd.frogans.fnc;application/vnd.frogans.ltf;application/vnd.fujixerox.ddd;application/vnd.fujixerox.docuworks;application/vnd.fujixerox.docuworks.binder;application/vnd.fujitu.oasys;application/vnd.fujitsu.oasys2;application/vnd.fujitsu.oasys3;application/vnd.fujitsu.oasysgp;application/vnd.fujitsu.oasysprs;application/x-futuresplash;application/vnd.fuzzysheet;image/g3fax;application/vnd.gmx;model/vn.gtw;application/vnd.genomatix.tuxedo;application/vnd.geogebra.file;application/vnd.geogebra.tool;model/vnd.gdl;application/vnd.geometry-explorer;application/vnd.geonext;application/vnd.geoplan;application/vnd.geospace;applicatio/x-font-ghostscript;application/x-font-bdf;application/x-gtar;application/x-texinfo;application/x-gnumeric;application/vnd.google-earth.kml+xml;application/vnd.google-earth.kmz;application/vnd.grafeq;image/gif;text/vnd.graphviz;aplication/vnd.groove-account;application/vnd.groove-help;application/vnd.groove-identity-message;application/vnd.groove-injector;application/vnd.groove-tool-message;application/vnd.groove-tool-template;application/vnd.groove-vcar;video/h261;video/h263;video/h264;application/vnd.hp-hpid;application/vnd.hp-hps;application/x-hdf;audio/vnd.rip;application/vnd.hbci;application/vnd.hp-jlyt;application/vnd.hp-pcl;application/vnd.hp-hpgl;application/vnd.yamaha.h-script;application/vnd.yamaha.hv-dic;application/vnd.yamaha.hv-voice;application/vnd.hydrostatix.sof-data;application/hyperstudio;application/vnd.hal+xml;text/html;application/vnd.ibm.rights-management;application/vnd.ibm.securecontainer;text/calendar;application/vnd.iccprofile;image/x-icon;application/vnd.igloader;image/ief;application/vnd.immervision-ivp;application/vnd.immervision-ivu;application/reginfo+xml;text/vnd.in3d.3dml;text/vnd.in3d.spot;mode/iges;application/vnd.intergeo;application/vnd.cinderella;application/vnd.intercon.formnet;application/vnd.isac.fcs;application/ipfix;application/pkix-cert;application/pkixcmp;application/pkix-crl;application/pkix-pkipath;applicaion/vnd.insors.igm;application/vnd.ipunplugged.rcprofile;application/vnd.irepository.package+xml;text/vnd.sun.j2me.app-descriptor;application/java-archive;application/java-vm;application/x-java-jnlp-file;application/java-serializd-object;text/x-java-source,java;application/javascript;application/json;application/vnd.joost.joda-archive;video/jpm;image/jpeg;video/jpeg;application/vnd.kahootz;application/vnd.chipnuts.karaoke-mmd;application/vnd.kde.karbon;aplication/vnd.kde.kchart;application/vnd.kde.kformula;application/vnd.kde.kivio;application/vnd.kde.kontour;application/vnd.kde.kpresenter;application/vnd.kde.kspread;application/vnd.kde.kword;application/vnd.kenameaapp;applicatin/vnd.kidspiration;application/vnd.kinar;application/vnd.kodak-descriptor;application/vnd.las.las+xml;application/x-latex;application/vnd.llamagraphics.life-balance.desktop;application/vnd.llamagraphics.life-balance.exchange+xml;application/vnd.jam;application/vnd.lotus-1-2-3;application/vnd.lotus-approach;application/vnd.lotus-freelance;application/vnd.lotus-notes;application/vnd.lotus-organizer;application/vnd.lotus-screencam;application/vnd.lotus-wordro;audio/vnd.lucent.voice;audio/x-mpegurl;video/x-m4v;application/mac-binhex40;application/vnd.macports.portpkg;application/vnd.osgeo.mapguide.package;application/marc;application/marcxml+xml;application/mxf;application/vnd.wolfrm.player;application/mathematica;application/mathml+xml;application/mbox;application/vnd.medcalcdata;application/mediaservercontrol+xml;application/vnd.mediastation.cdkey;application/vnd.mfer;application/vnd.mfmp;model/mesh;appliation/mads+xml;application/mets+xml;application/mods+xml;application/metalink4+xml;application/vnd.ms-powerpoint.template.macroenabled.12;application/vnd.ms-word.document.macroenabled.12;application/vnd.ms-word.template.macroenabed.12;application/vnd.mcd;application/vnd.micrografx.flo;application/vnd.micrografx.igx;application/vnd.eszigno3+xml;application/x-msaccess;video/x-ms-asf;application/x-msdownload;application/vnd.ms-artgalry;application/vnd.ms-ca-compressed;application/vnd.ms-ims;application/x-ms-application;application/x-msclip;image/vnd.ms-modi;application/vnd.ms-fontobject;application/vnd.ms-excel;application/vnd.ms-excel.addin.macroenabled.12;application/vnd.ms-excelsheet.binary.macroenabled.12;application/vnd.ms-excel.template.macroenabled.12;application/vnd.ms-excel.sheet.macroenabled.12;application/vnd.ms-htmlhelp;application/x-mscardfile;application/vnd.ms-lrm;application/x-msmediaview;aplication/x-msmoney;application/vnd.openxmlformats-officedocument.presentationml.presentation;application/vnd.openxmlformats-officedocument.presentationml.slide;application/vnd.openxmlformats-officedocument.presentationml.slideshw;application/vnd.openxmlformats-officedocument.presentationml.template;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;application/vnd.openxmlformats-officedocument.spreadsheetml.template;application/vnd.openxmformats-officedocument.wordprocessingml.document;application/vnd.openxmlformats-officedocument.wordprocessingml.template;application/x-msbinder;application/vnd.ms-officetheme;application/onenote;audio/vnd.ms-playready.media.pya;vdeo/vnd.ms-playready.media.pyv;application/vnd.ms-powerpoint;application/vnd.ms-powerpoint.addin.macroenabled.12;application/vnd.ms-powerpoint.slide.macroenabled.12;application/vnd.ms-powerpoint.presentation.macroenabled.12;appliation/vnd.ms-powerpoint.slideshow.macroenabled.12;application/vnd.ms-project;application/x-mspublisher;application/x-msschedule;application/x-silverlight-app;application/vnd.ms-pki.stl;application/vnd.ms-pki.seccat;application/vn.visio;video/x-ms-wm;audio/x-ms-wma;audio/x-ms-wax;video/x-ms-wmx;application/x-ms-wmd;application/vnd.ms-wpl;application/x-ms-wmz;video/x-ms-wmv;video/x-ms-wvx;application/x-msmetafile;application/x-msterminal;application/msword;application/x-mswrite;application/vnd.ms-works;application/x-ms-xbap;application/vnd.ms-xpsdocument;audio/midi;application/vnd.ibm.minipay;application/vnd.ibm.modcap;application/vnd.jcp.javame.midlet-rms;application/vnd.tmobile-ivetv;application/x-mobipocket-ebook;application/vnd.mobius.mbk;application/vnd.mobius.dis;application/vnd.mobius.plc;application/vnd.mobius.mqy;application/vnd.mobius.msl;application/vnd.mobius.txf;application/vnd.mobius.daf;tex/vnd.fly;application/vnd.mophun.certificate;application/vnd.mophun.application;video/mj2;audio/mpeg;video/vnd.mpegurl;video/mpeg;application/mp21;audio/mp4;video/mp4;application/mp4;application/vnd.apple.mpegurl;application/vnd.msician;application/vnd.muvee.style;application/xv+xml;application/vnd.nokia.n-gage.data;application/vnd.nokia.n-gage.symbian.install;application/x-dtbncx+xml;application/x-netcdf;application/vnd.neurolanguage.nlu;application/vnd.na;application/vnd.noblenet-directory;application/vnd.noblenet-sealer;application/vnd.noblenet-web;application/vnd.nokia.radio-preset;application/vnd.nokia.radio-presets;text/n3;application/vnd.novadigm.edm;application/vnd.novadim.edx;application/vnd.novadigm.ext;application/vnd.flographit;audio/vnd.nuera.ecelp4800;audio/vnd.nuera.ecelp7470;audio/vnd.nuera.ecelp9600;application/oda;application/ogg;audio/ogg;video/ogg;application/vnd.oma.dd2+xml;applicatin/vnd.oasis.opendocument.text-web;application/oebps-package+xml;application/vnd.intu.qbo;application/vnd.openofficeorg.extension;application/vnd.yamaha.openscoreformat;audio/webm;video/webm;application/vnd.oasis.opendocument.char;application/vnd.oasis.opendocument.chart-template;application/vnd.oasis.opendocument.database;application/vnd.oasis.opendocument.formula;application/vnd.oasis.opendocument.formula-template;application/vnd.oasis.opendocument.grapics;application/vnd.oasis.opendocument.graphics-template;application/vnd.oasis.opendocument.image;application/vnd.oasis.opendocument.image-template;application/vnd.oasis.opendocument.presentation;application/vnd.oasis.opendocumen.presentation-template;application/vnd.oasis.opendocument.spreadsheet;application/vnd.oasis.opendocument.spreadsheet-template;application/vnd.oasis.opendocument.text;application/vnd.oasis.opendocument.text-master;application/vnd.asis.opendocument.text-template;image/ktx;application/vnd.sun.xml.calc;application/vnd.sun.xml.calc.template;application/vnd.sun.xml.draw;application/vnd.sun.xml.draw.template;application/vnd.sun.xml.impress;application/vnd.sun.xl.impress.template;application/vnd.sun.xml.math;application/vnd.sun.xml.writer;application/vnd.sun.xml.writer.global;application/vnd.sun.xml.writer.template;application/x-font-otf;application/vnd.yamaha.openscoreformat.osfpvg+xml;application/vnd.osgi.dp;application/vnd.palm;text/x-pascal;application/vnd.pawaafile;application/vnd.hp-pclxl;application/vnd.picsel;image/x-pcx;image/vnd.adobe.photoshop;application/pics-rules;image/x-pict;application/x-chat;aplication/pkcs10;application/x-pkcs12;application/pkcs7-mime;application/pkcs7-signature;application/x-pkcs7-certreqresp;application/x-pkcs7-certificates;application/pkcs8;application/vnd.pocketlearn;image/x-portable-anymap;image/-portable-bitmap;application/x-font-pcf;application/font-tdpfr;application/x-chess-pgn;image/x-portable-graymap;image/png;image/x-portable-pixmap;application/pskc+xml;application/vnd.ctc-posml;application/postscript;application/xfont-type1;application/vnd.powerbuilder6;application/pgp-encrypted;application/pgp-signature;application/vnd.previewsystems.box;application/vnd.pvi.ptid1;application/pls+xml;application/vnd.pg.format;application/vnd.pg.osasli;tex/prs.lines.tag;application/x-font-linux-psf;application/vnd.publishare-delta-tree;application/vnd.pmi.widget;application/vnd.quark.quarkxpress;application/vnd.epson.esf;application/vnd.epson.msf;application/vnd.epson.ssf;applicaton/vnd.epson.quickanime;application/vnd.intu.qfx;video/quicktime;application/x-rar-compressed;audio/x-pn-realaudio;audio/x-pn-realaudio-plugin;application/rsd+xml;application/vnd.rn-realmedia;application/vnd.realvnc.bed;applicatin/vnd.recordare.musicxml;application/vnd.recordare.musicxml+xml;application/relax-ng-compact-syntax;application/vnd.data-vision.rdz;application/rdf+xml;application/vnd.cloanto.rp9;application/vnd.jisp;application/rtf;text/richtex;application/vnd.route66.link66+xml;application/rss+xml;application/shf+xml;application/vnd.sailingtracker.track;image/svg+xml;application/vnd.sus-calendar;application/sru+xml;application/set-payment-initiation;application/set-reistration-initiation;application/vnd.sema;application/vnd.semd;application/vnd.semf;application/vnd.seemail;application/x-font-snf;application/scvp-vp-request;application/scvp-vp-response;application/scvp-cv-request;application/svp-cv-response;application/sdp;text/x-setext;video/x-sgi-movie;application/vnd.shana.informed.formdata;application/vnd.shana.informed.formtemplate;application/vnd.shana.informed.interchange;application/vnd.shana.informed.package;application/thraud+xml;application/x-shar;image/x-rgb;application/vnd.epson.salt;application/vnd.accpac.simply.aso;application/vnd.accpac.simply.imp;application/vnd.simtech-mindmapper;application/vnd.commonspace;application/vnd.ymaha.smaf-audio;application/vnd.smaf;application/vnd.yamaha.smaf-phrase;application/vnd.smart.teacher;application/vnd.svd;application/sparql-query;application/sparql-results+xml;application/srgs;application/srgs+xml;application/sml+xml;application/vnd.koan;text/sgml;application/vnd.stardivision.calc;application/vnd.stardivision.draw;application/vnd.stardivision.impress;application/vnd.stardivision.math;application/vnd.stardivision.writer;application/vnd.tardivision.writer-global;application/vnd.stepmania.stepchart;application/x-stuffit;application/x-stuffitx;application/vnd.solent.sdkm+xml;application/vnd.olpc-sugar;audio/basic;application/vnd.wqd;application/vnd.symbian.install;application/smil+xml;application/vnd.syncml+xml;application/vnd.syncml.dm+wbxml;application/vnd.syncml.dm+xml;application/x-sv4cpio;application/x-sv4crc;application/sbml+xml;text/tab-separated-values;image/tiff;application/vnd.to.intent-module-archive;application/x-tar;application/x-tcl;application/x-tex;application/x-tex-tfm;application/tei+xml;text/plain;application/vnd.spotfire.dxp;application/vnd.spotfire.sfs;application/timestamped-data;applicationvnd.trid.tpt;application/vnd.triscape.mxs;text/troff;application/vnd.trueapp;application/x-font-ttf;text/turtle;application/vnd.umajin;application/vnd.uoml+xml;application/vnd.unity;application/vnd.ufdl;text/uri-list;application/nd.uiq.theme;application/x-ustar;text/x-uuencode;text/x-vcalendar;text/x-vcard;application/x-cdlink;application/vnd.vsf;model/vrml;application/vnd.vcx;model/vnd.mts;model/vnd.vtu;application/vnd.visionary;video/vnd.vivo;applicatin/ccxml+xml,;application/voicexml+xml;application/x-wais-source;application/vnd.wap.wbxml;image/vnd.wap.wbmp;audio/x-wav;application/davmount+xml;application/x-font-woff;application/wspolicy+xml;image/webp;application/vnd.webturb;application/widget;application/winhlp;text/vnd.wap.wml;text/vnd.wap.wmlscript;application/vnd.wap.wmlscriptc;application/vnd.wordperfect;application/vnd.wt.stf;application/wsdl+xml;image/x-xbitmap;image/x-xpixmap;image/x-xwindowump;application/x-x509-ca-cert;application/x-xfig;application/xhtml+xml;application/xml;application/xcap-diff+xml;application/xenc+xml;application/patch-ops-error+xml;application/resource-lists+xml;application/rls-services+xml;aplication/resource-lists-diff+xml;application/xslt+xml;application/xop+xml;application/x-xpinstall;application/xspf+xml;application/vnd.mozilla.xul+xml;chemical/x-xyz;text/yaml;application/yang;application/yin+xml;application/vnd.ul;application/zip;application/vnd.handheld-entertainment+xml;application/vnd.zzazz.deck+xml")
                        .addPreference("browser.helperApps.alwaysAsk.force", false)
                        .addPreference("browser.download.manager.showWhenStarting",false)
                        .addPreference("pdfjs.disabled", true);  // disable the built-in PDF viewer;
                webdriver =new FirefoxDriver(options);*/
                webdriver =new FirefoxDriver();
                logger.info("Браузер Firefox запущен (Код 1.5)");
            } catch (Exception e) {
                logger.warning("Ошибка загрузки драйвера Firefox браузера (Код 1.8)");
            }
        }

        if (webdriver == null && browserName.equals("firefox") ){
            try {
                webdriver =new FirefoxDriver();
                logger.info("Браузер Firefox запущен (Код 1.5)");
            } catch (Exception e) {
                logger.warning("Ошибка загрузки драйвера Firefox браузера (Код 1.8)");
            }
        }

        if (webdriver == null && browserName.equals("firefox") ){
            try {
                webdriver =new FirefoxDriver();
                logger.info("Браузер Firefox запущен (Код 1.5)");
            } catch (Exception e) {
                logger.warning("Ошибка загрузки драйвера Firefox браузера (Код 1.8)");
                browserName = "chrome";
            }
        }

        if (browserName.equals("chrome") ){
            try {
                webdriver=new ChromeDriver();
                logger.info("Браузер Chrome запущен (Код 1.6)");
            } catch (Exception e) {
                logger.warning("Ошибка загрузки драйвера Chrome браузера (Код 1.9)");
                browserName = "ie";
            }
        }

        if (browserName.equals("ie") ){
            try {
                webdriver=new InternetExplorerDriver();
                logger.info("Браузер Internet Explorer запущен (Код 1.7)");
            } catch (Exception e) {
                logger.warning("Ошибка загрузки драйвера Internet Explorer браузера (Код 2.0)");
            }
        }

        if (webdriver !=null){

            SideFile sideFile = null;
            Gson gson = new Gson();
            String text = "";

            if(!isTest) {
                try {
                    text = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    logger.warning("Ошибка открытия файла команд  (Код 2.2)");
                }

                try {
                    sideFile = gson.fromJson(text, SideFile.class);
                }catch (Exception e) {
                    logger.warning("Ошибка преобразования файла команд (Код 0.4)");
                }

                // Удаление загруженного файла
                if(f.delete()){
                }else logger.warning("Ошибка удаления файла (Код 0.7)");
            }


            //webdriver.manage().window().setPosition(new Point(0, -3000));
            if(!isTest) {
                webdriver.manage().window().maximize();
                for (Test test : sideFile.tests) {
                    for (Command command : test.commands) {
                        if (command.value.contains("#!")) {
                            logger.info("Найдена последовательность (Код 3.0): #! в значении: " + command.value + " команда: " + command.command + " таргет: " + command.target);
                        } else {
                            if(command.comment.contains("Show")){
                                webdriver.manage().window().fullscreen();
                            }
                            runCommand(webdriver, sideFile.url, command.command, command.target, command.value, command.comment);
                        }
                    }
                }
                webdriver.manage().window().maximize();
                //webdriver.close();
            }
            else {
                //webdriver.manage().window().maximize();
                webdriver.quit();
                logger.info("Браузер закрыт (Код 1.3)");
            }
        }
        else
        {
            logger.warning("Не найден указанный драйвер браузера (Код 0.5)");
        }

        logger.info("Завершение работы приложения (Код 2.1)");
    }

    private static void Log(String fileNameLog) throws SecurityException,IOException{
        File f = new File(fileNameLog);
        if(!f.exists()){
            f.createNewFile();
        }

        fh = new FileHandler(fileNameLog,true);
        logger = Logger.getLogger("wws");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    private static void initialLog(Boolean isTest){
        try {
            Log(fileName + (isTest ? System.currentTimeMillis():"")+".log");
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
        }
    }


    private static By getLocator(String target){
        String[] locatorItems = target.split("=",2);
        LocatorType locatorType = LocatorType.valueOf(locatorItems[0]);

        switch(locatorType) {

            case id :{
                return By.id(locatorItems[1]);
            }

            case name:{
                return By.name(locatorItems[1]);
            }

            case css:{
                return By.cssSelector(locatorItems[1]);
            }

            case xpath:{
                return By.xpath(locatorItems[1]);
            }

            case tag:{
                return By.tagName(locatorItems[1]);
            }

            case text:{
                return By.linkText(locatorItems[1]);
            }

            case partText:{
                return By.partialLinkText(locatorItems[1]);
            }

            case partialLinkText:{
                return By.partialLinkText(locatorItems[1]);
            }

            case linkText:{
                return By.partialLinkText(locatorItems[1]);
            }

            default:{
                logger.warning("Не найден локатор (Код 1.1): " + locatorItems[0]);
            }
        }
        return null;
    }

    private static WebElement getWebElement(WebDriver  webdriver,String target) {
        try {
            WebDriverWait myWaitVar = new WebDriverWait(webdriver,15);
            return myWaitVar.until(ExpectedConditions.elementToBeClickable(getLocator(target)));
        } catch (Exception e) {
            logger.warning("Не найден элемент (Код 3.1) target: " + target);
            webdriver.manage().window().maximize();
            return  null;
        }
    }

    private static void runCommand(WebDriver  webdriver,String url, String command,String target,String value,String comment){




        Actions builder = null;
        Action seriesOfActions = null;

        try {

            switch (command) {

                case "open": {
                    webdriver.get(url + target);
                    break;
                }

                case "click": {

                    WebElement webElement = getWebElement(webdriver,target);
                    // webdriver.findElement(getLocator(target)).click();
                    builder = new Actions(webdriver);
                    seriesOfActions = builder.moveToElement(webElement).build();
                    seriesOfActions.perform();
                    getWebElement(webdriver,target).click();

                    Boolean isEnd = comment.contains("end");
                    Boolean isEnter = comment.contains("enter");

                    if(isEnd){
                        webElement.sendKeys(Keys.END);
                    }

                    if(isEnter){
                        webElement.sendKeys(Keys.ENTER);
                    }

                    break;
                }

                case "clickAt": {
                    WebElement webElement = webdriver.findElement(getLocator(target));
                    webElement.click();

                    Boolean isEnd = comment.contains("end");
                    Boolean isEnter = comment.contains("enter");

                    if(isEnd){
                        webElement.sendKeys(Keys.END);
                    }

                    if(isEnter){
                        webElement.sendKeys(Keys.ENTER);
                    }
                    break;
                }

                case "captureScreenshot": {
                    takeSnapShot(webdriver,"c:\\selenium\\SnapShot\\");
                    break;
                }

                case "type": {
                    WebElement webElement = webdriver.findElement(getLocator(target));

                    // очистка
//                    Actions ActClear = new Actions(webdriver);
//                    ActClear.clickAndHold(webElement).sendKeys(Keys.chord(Keys.CONTROL, Keys.HOME)).build().perform();
//                    ActClear.clickAndHold(webElement).sendKeys(Keys.chord(Keys.CONTROL,Keys.SHIFT,Keys.END)).build().perform();
                    //webElement.clear();
//                    webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//                    webElement.sendKeys(Keys.chord(Keys.BACK_SPACE));
                    builder = new Actions(webdriver);
                    seriesOfActions = builder.moveToElement(webElement).build();
                    seriesOfActions.perform();
                    webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME);
                    webElement.sendKeys(value);
//                    webElement.clear();
                    // заполнение
                    //webElement.sendKeys(value);
//                    Actions  builder = new Actions(webdriver);
//                    Action seriesOfActions = builder.moveToElement(webElement).click(webElement).keyDown(webElement,Keys.SHIFT).sendKeys(webElement,value).keyUp(webElement,Keys.SHIFT).build();
//                    seriesOfActions.perform();
                    break;
                }

                case "sendKeys": {
                    WebElement webElement = webdriver.findElement(getLocator(target));
                    Boolean isNoRez = true;
                    String[] locatorItems = null;
                    String targetSpecialClick = null;
                    WebElement webElementSpecialClick = null;
                    Boolean isContains = comment.contains("contains");
                    Boolean isFirstSendKeys = comment.contains("SendKeys");
                    Boolean isClick = comment.contains("click");
                    Boolean isSpecialClick = comment.contains("SpecialClick");
                    Boolean isWithoutSpaces = comment.contains("WithoutSpaces");
                    Boolean isEnd = comment.contains("end");
                    Boolean isEnter = comment.contains("enter");

                    // очистка
//                    Actions ActClear = new Actions(webdriver);
//                    ActClear.clickAndHold(webElement).sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
                    //webElement.clear();
//                    webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//                    webElement.sendKeys(Keys.chord(Keys.BACK_SPACE));
                    if(value.contains("KEY_ENTER")){
                        webElement.sendKeys(Keys.ENTER);
                        break;
                    }

                    if (isFirstSendKeys){
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                    }else
                    {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).build();
                        seriesOfActions.perform();
                        webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME);
                        webElement.sendKeys(value);
                    }

                    if (isNoRez && isClick){
                        webElement.click();
                    }

                    if(isNoRez && isEnd){
                        webElement.sendKeys(Keys.END);
                    }

                    if(isNoRez && isEnter){
                        webElement.sendKeys(Keys.ENTER);
                    }

                    if(isSpecialClick){
                        locatorItems = comment.split("=",2);
                        targetSpecialClick = locatorItems[1];
                        webElementSpecialClick = webdriver.findElement(getLocator(targetSpecialClick));
                        webElementSpecialClick.click();
                    }

                    webElement = webdriver.findElement(getLocator(target));
                    String strVal = webElement.getAttribute("value");
                    if(isWithoutSpaces){
                        strVal = strVal.replaceAll("\\s+","");
                    }
                    isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));
                    if ( isNoRez ) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).click(webElement).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME);
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).click(webElement).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }


                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).build();
                        seriesOfActions.perform();


                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        webElement = webdriver.findElement(getLocator(target));
                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));


                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if ( isNoRez ) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).click(webElement).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME);
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.moveToElement(webElement).click(webElement).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }


                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).keyDown(webElement, Keys.SHIFT).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).keyUp(webElement, Keys.SHIFT).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));

                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.click(webElement).sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).build();
                        seriesOfActions.perform();


                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                        strVal = webElement.getAttribute("value");
                        if(isWithoutSpaces){
                            strVal = strVal.replaceAll("\\s+","");
                        }
                        isNoRez = (isContains ? !strVal.contains(value) : (strVal.compareToIgnoreCase(value) !=0));


                    }

                    if (isNoRez) {
                        builder = new Actions(webdriver);
                        seriesOfActions = builder.sendKeys(webElement,Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),Keys.BACK_SPACE,Keys.HOME).sendKeys(webElement, value).build();
                        seriesOfActions.perform();

                        if (isClick){
                            webElement.click();
                        }

                        if(isEnd){
                            webElement.sendKeys(Keys.END);
                        }

                        if(isEnter){
                            webElement.sendKeys(Keys.ENTER);
                        }

                        if(isSpecialClick){
                            webElementSpecialClick.click();
                            webElement.click();
                        }

                    }

                    break;
                }

                case "select": {
                    String[] Items = value.split("=",2);
                    Select select = new Select(webdriver.findElement(getLocator(target)));
                    select.selectByVisibleText(Items[1]);
                    break;
                }

                case "mouseDownAt":{

                    WebElement webElement = getWebElement(webdriver,target);
                    builder = new Actions(webdriver);
                    seriesOfActions = builder.clickAndHold(webElement).build();
                    seriesOfActions.perform();

                    break;
                }

                case "mouseUpAt":{

                    WebElement webElement = getWebElement(webdriver,target);
                    builder = new Actions(webdriver);
                    seriesOfActions = builder.release(webElement).build();
                    seriesOfActions.perform();
                    break;
                }

                case "mouseOut":{

                    WebElement webElement = getWebElement(webdriver,target);
                    mouseOut(webdriver,webElement);
                    break;
                }

                default: {
                    logger.warning("Не найдена команда (Код 1.2): " + command);
                    webdriver.manage().window().maximize();
                }
            }
        }
        catch (Exception e){
            logger.warning("Ошибка  при выполненни команды (Код 0.1) command: " + command+ " target: " + target + " value: " + value);
            webdriver.manage().window().maximize();
        }

    }

    private static void mouseOut(WebDriver  webdriver,WebElement element) {
        String code = "var fireOnThis = arguments[0];"
                + "var evObj = document.createEvent('MouseEvents');"
                + "evObj.initEvent( 'mouseout', true, true );"
                + "fireOnThis.dispatchEvent(evObj);";
        ((JavascriptExecutor) webdriver).executeScript(code, element);
    }

    private static void takeSnapShot(WebDriver  webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile=new File(fileWithPath);
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };

        Files.copy(SrcFile.toPath(), DestFile.toPath(),options);
    }


    // Описание Side файла
    private class SideFile
    {
        //public String id;
        //public String name;
        public String url;
        public ArrayList<Test> tests;
        //public ArrayList<Suite> suites;
        //public ArrayList<String> urls;
        //public ArrayList<String> plugins;
        //public String version;
    }

    public class Test
    {
        //public String id;
        public String name;
        public ArrayList<Command> commands;
    }

    public class Command
    {
        //public String id;
        public String comment;
        public String command;
        public String target;
        public String value;

    }

    public class Suite
    {
        public String id;
        public String name;
        public Boolean parallel;
        public Integer timeout;
        public ArrayList<String> tests;
    }

    // Конец описания Side файла

}
