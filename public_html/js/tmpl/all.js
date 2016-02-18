define(function () { return function (__fest_context){"use strict";var __fest_self=this,__fest_buf="",__fest_chunks=[],__fest_chunk,__fest_attrs=[],__fest_select,__fest_if,__fest_iterator,__fest_to,__fest_fn,__fest_html="",__fest_blocks={},__fest_params,__fest_element,__fest_debug_file="",__fest_debug_line="",__fest_debug_block="",__fest_htmlchars=/[&<>"]/g,__fest_htmlchars_test=/[&<>"]/,__fest_short_tags = {"area":true,"base":true,"br":true,"col":true,"command":true,"embed":true,"hr":true,"img":true,"input":true,"keygen":true,"link":true,"meta":true,"param":true,"source":true,"wbr":true},__fest_element_stack = [],__fest_htmlhash={"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;"},__fest_jschars=/[\\'"\/\n\r\t\b\f<>]/g,__fest_jschars_test=/[\\'"\/\n\r\t\b\f<>]/,__fest_jshash={"\"":"\\\"","\\":"\\\\","/":"\\/","\n":"\\n","\r":"\\r","\t":"\\t","\b":"\\b","\f":"\\f","'":"\\'","<":"\\u003C",">":"\\u003E"},___fest_log_error;if(typeof __fest_error === "undefined"){___fest_log_error = (typeof console !== "undefined" && console.error) ? function(){return Function.prototype.apply.call(console.error, console, arguments)} : function(){};}else{___fest_log_error=__fest_error};function __fest_log_error(msg){___fest_log_error(msg+"\nin block \""+__fest_debug_block+"\" at line: "+__fest_debug_line+"\nfile: "+__fest_debug_file)}function __fest_replaceHTML(chr){return __fest_htmlhash[chr]}function __fest_replaceJS(chr){return __fest_jshash[chr]}function __fest_extend(dest, src){for(var i in src)if(src.hasOwnProperty(i))dest[i]=src[i];}function __fest_param(fn){fn.param=true;return fn}function __fest_call(fn, params,cp){if(cp)for(var i in params)if(typeof params[i]=="function"&&params[i].param)params[i]=params[i]();return fn.call(__fest_self,params)}function __fest_escapeJS(s){if (typeof s==="string") {if (__fest_jschars_test.test(s))return s.replace(__fest_jschars,__fest_replaceJS);} else if (typeof s==="undefined")return "";return s;}function __fest_escapeHTML(s){if (typeof s==="string") {if (__fest_htmlchars_test.test(s))return s.replace(__fest_htmlchars,__fest_replaceHTML);} else if (typeof s==="undefined")return "";return s;}var params=__fest_context;(function(__fest_context){__fest_blocks.btn=function(params){var __fest_buf="";try{__fest_if=params.href}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){try{__fest_attrs[0]=__fest_escapeHTML(params.href)}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}__fest_buf+=("<a href=\"" + __fest_attrs[0] + "\" class=\"btn\"");try{__fest_if=params.props}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){var i,prop,__fest_iterator0;try{__fest_iterator0=params.props || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){prop=__fest_iterator0[i];try{__fest_select=(i)}catch(e){__fest_select="";__fest_log_error(e.message)}if(__fest_select!==""){__fest_buf+=(" " + __fest_select + "=\"");try{__fest_buf+=(__fest_escapeHTML(prop))}catch(e){__fest_log_error(e.message + "8");}__fest_buf+=("\"");}}}__fest_buf+=(">");try{__fest_buf+=(__fest_escapeHTML(params.title))}catch(e){__fest_log_error(e.message + "12");}__fest_buf+=("</a>");}else{try{__fest_attrs[0]=__fest_escapeHTML(params.classes && params.classes.length ? params.classes.join(' ') : '')}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}__fest_buf+=("<button class=\"btn " + __fest_attrs[0] + "\"");try{__fest_if=params.props}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){var i,prop,__fest_iterator1;try{__fest_iterator1=params.props || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator1){prop=__fest_iterator1[i];try{__fest_select=(i)}catch(e){__fest_select="";__fest_log_error(e.message)}if(__fest_select!==""){__fest_buf+=(" " + __fest_select + "=\"");try{__fest_buf+=(__fest_escapeHTML(prop))}catch(e){__fest_log_error(e.message + "19");}__fest_buf+=("\"");}}}__fest_buf+=(">");try{__fest_buf+=(__fest_escapeHTML(params.title))}catch(e){__fest_log_error(e.message + "23");}__fest_buf+=("</button>");}return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.preloader=function(params){var __fest_buf="";__fest_buf+=("<div class=\"overlay\"></div><div class=\"preloader\"></div><div class=\"preload-text\"><div class=\"preload-text__text js-text\"></div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){(function(__fest_context){__fest_blocks.btn=function(params){var __fest_buf="";try{__fest_if=params.href}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){try{__fest_attrs[0]=__fest_escapeHTML(params.href)}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}__fest_buf+=("<a href=\"" + __fest_attrs[0] + "\" class=\"btn\"");try{__fest_if=params.props}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){var i,prop,__fest_iterator0;try{__fest_iterator0=params.props || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){prop=__fest_iterator0[i];try{__fest_select=(i)}catch(e){__fest_select="";__fest_log_error(e.message)}if(__fest_select!==""){__fest_buf+=(" " + __fest_select + "=\"");try{__fest_buf+=(__fest_escapeHTML(prop))}catch(e){__fest_log_error(e.message + "8");}__fest_buf+=("\"");}}}__fest_buf+=(">");try{__fest_buf+=(__fest_escapeHTML(params.title))}catch(e){__fest_log_error(e.message + "12");}__fest_buf+=("</a>");}else{try{__fest_attrs[0]=__fest_escapeHTML(params.classes && params.classes.length ? params.classes.join(' ') : '')}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}__fest_buf+=("<button class=\"btn " + __fest_attrs[0] + "\"");try{__fest_if=params.props}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){var i,prop,__fest_iterator1;try{__fest_iterator1=params.props || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator1){prop=__fest_iterator1[i];try{__fest_select=(i)}catch(e){__fest_select="";__fest_log_error(e.message)}if(__fest_select!==""){__fest_buf+=(" " + __fest_select + "=\"");try{__fest_buf+=(__fest_escapeHTML(prop))}catch(e){__fest_log_error(e.message + "19");}__fest_buf+=("\"");}}}__fest_buf+=(">");try{__fest_buf+=(__fest_escapeHTML(params.title))}catch(e){__fest_log_error(e.message + "23");}__fest_buf+=("</button>");}return __fest_buf;};})(__fest_context);__fest_blocks.form=function(params){var __fest_buf="";__fest_buf+=("<div class=\"form\">");try{__fest_attrs[0]=__fest_escapeHTML(params.type)}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}try{__fest_attrs[1]=__fest_escapeHTML(params.type)}catch(e){__fest_attrs[1]=""; __fest_log_error(e.message);}__fest_buf+=("<form action=\"\/api\/v1\/auth\/" + __fest_attrs[0] + "\" method=\"POST\" id=\"" + __fest_attrs[1] + "-form\"><div><input type=\"text\" name=\"login\" class=\"form__input\" placeholder=\"Логин...\" required=\"\"/></div>");try{__fest_if=params.type === 'signup'}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){__fest_buf+=("<div><input type=\"text\" name=\"email\" class=\"form__input\" placeholder=\"E-mail...\" required=\"\"/></div>");}__fest_buf+=("<div><input type=\"password\" name=\"password\" class=\"form__input\" placeholder=\"Пароль...\" required=\"\"/></div>");__fest_select="btn";__fest_params={};try{__fest_params={title: 'Войти'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</form></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.app=function(params){var __fest_buf="";__fest_buf+=("<div class=\"app\"></div>");__fest_select="preloader";__fest_params={};__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("<div class=\"error\"><div class=\"error__text js-text\">error</div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.layer=function(params){var __fest_buf="";__fest_buf+=("<div class=\"layer layer_transparent\"></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.cards=function(params){var __fest_buf="";__fest_buf+=("<div class=\"cards\"><div class=\"cards__wrap js-cards\"></div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.color=function(params){var __fest_buf="";__fest_buf+=("<div class=\"color\"><div class=\"color__value color__value_red js-color\" data-color=\"red\"></div><div class=\"color__value color__value_yellow js-color\" data-color=\"yellow\"></div><div class=\"color__value color__value_green js-color\" data-color=\"green\"></div><div class=\"color__value color__value_blue js-color\" data-color=\"blue\"></div>");__fest_select="btn";__fest_params={};try{__fest_params={title: 'Закрыть', classes:['js-close']}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.deck=function(params){var __fest_buf="";__fest_buf+=("<div class=\"b-table js-table\"><div class=\"b-table__deck js-deck\"></div><div class=\"b-table__current\"></div><div class=\"b-table__uno js-uno\">UNO!</div><div class=\"b-table__new\"></div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.end=function(params){var __fest_buf="";__fest_buf+=("<div class=\"end\"><div class=\"end__title\">Game Over!</div><div class=\"end__scores\">");var i,score,__fest_iterator0;try{__fest_iterator0=params.scores || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){score=__fest_iterator0[i];__fest_buf+=("<div class=\"end__score\"><div class=\"end__score__login\">");try{__fest_buf+=(__fest_escapeHTML(score.login))}catch(e){__fest_log_error(e.message + "7");}__fest_buf+=("</div><div class=\"end__score__value\">");try{__fest_buf+=(__fest_escapeHTML(score.score))}catch(e){__fest_log_error(e.message + "8");}__fest_buf+=("</div></div>");}__fest_buf+=("</div><div class=\"end__btn\">");__fest_select="btn";__fest_params={};try{__fest_params={title: 'Продолжить', href:'/'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.game=function(params){var __fest_buf="";__fest_buf+=("<div class=\"game\"></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks["game-settings"]=function(params){var __fest_buf="";__fest_buf+=("<div class=\"game-settings\"><div class=\"game-settings__title\">Выберите желаемое количество игроков</div><div class=\"b-select\"><div class=\"b-select__btn b-select__btn_selected\" data-val=\"2\"><div class=\"b-select__img\"></div><div class=\"b-select__text\"></div></div><div class=\"b-select__btn\" data-val=\"3\"><div class=\"b-select__img\"></div><div class=\"b-select__text\"></div></div><div class=\"b-select__btn\" data-val=\"4\"><div class=\"b-select__img\"></div><div class=\"b-select__text\"></div></div><div class=\"b-select__btn\" data-val=\"5\"><div class=\"b-select__img\"></div><div class=\"b-select__text\"></div></div></div>");__fest_select="btn";__fest_params={};try{__fest_params={classes: ['js-send'], title: 'Готово'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_select="btn";__fest_params={};try{__fest_params={title: 'Назад', href: '#'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.header=function(params){var __fest_buf="";return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.login=function(params){var __fest_buf="";__fest_buf+=("<div class=\"login\">");__fest_select="form";__fest_params={};try{__fest_params={type: 'signin'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.main=function(params){var __fest_buf="";__fest_buf+=("<div class=\"main\"><div class=\"title\">UNO</div>");try{__fest_if=params.user.isLogined()}catch(e){__fest_if=false;__fest_log_error(e.message);}if(__fest_if){__fest_select="btn";__fest_params={};try{__fest_params={href: '#game', title: 'Играть'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_select="btn";__fest_params={};try{__fest_params={href: '#profile', title: 'Профиль'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_select="btn";__fest_params={};try{__fest_params={title: 'Выйти', classes: ['js-logout']}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);}else{__fest_select="btn";__fest_params={};try{__fest_params={href: '#login', title: 'Войти'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_select="btn";__fest_params={};try{__fest_params={href: '#signup', title: 'Регистрация'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);}__fest_select="btn";__fest_params={};try{__fest_params={href: '#scoreboard', title: 'Рейтинг'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks["page404"]=function(params){var __fest_buf="";return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.player=function(params){var __fest_buf="";__fest_buf+=("<div class=\"player\"><div class=\"player__login\">");try{__fest_buf+=(__fest_escapeHTML(params.player.login))}catch(e){__fest_log_error(e.message + "3");}__fest_buf+=("</div><div class=\"player-cards js-cards\">");var i,__fest_to0,__fest_from0,__fest_iterator0;try{__fest_from0=1;__fest_to0=params.player.cardsCount;}catch(e){__fest_from0=0;__fest_to0=0;__fest_log_error(e.message);}for(i = __fest_from0;i<=__fest_to0;i++){try{__fest_attrs[0]=__fest_escapeHTML(i == 1 ? ' player-cards__card_first' : '')}catch(e){__fest_attrs[0]=""; __fest_log_error(e.message);}try{__fest_attrs[1]=__fest_escapeHTML(i == params.player.cardsCount?' player-cards__card_last' : '')}catch(e){__fest_attrs[1]=""; __fest_log_error(e.message);}__fest_buf+=("<div class=\"player-cards__card" + __fest_attrs[0] + "" + __fest_attrs[1] + "\"></div>");}__fest_buf+=("</div></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.players=function(params){var __fest_buf="";__fest_buf+=("<div class=\"players\"></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.profile=function(params){var __fest_buf="";__fest_buf+=("<div class=\"profile\"><div class=\"profile__title\">Профиль</div><div class=\"email-field\"><div class=\"email-field__title\">E-mail:</div><div class=\"email-field__value\">");try{__fest_buf+=(__fest_escapeHTML(params.email))}catch(e){__fest_log_error(e.message + "6");}__fest_buf+=("</div></div><form method=\"POST\"><div class=\"login-field\"><div class=\"login-field__title\">Логин:</div><div class=\"email-field__value\">");try{__fest_buf+=(__fest_escapeHTML(params.login))}catch(e){__fest_log_error(e.message + "11");}__fest_buf+=("</div></div>");__fest_select="btn";__fest_params={};try{__fest_params={title: 'Назад', href: '#'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</form></div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.scoreboard=function(params){var __fest_buf="";__fest_buf+=("<div class=\"scoreboard\"><div class=\"scoreboard__title\">Рейтинг игроков</div>");var i,score,__fest_iterator0;try{__fest_iterator0=params.scores || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){score=__fest_iterator0[i];__fest_buf+=("<div class=\"score\"><div class=\"score__login\">");try{__fest_buf+=(__fest_escapeHTML(score.login))}catch(e){__fest_log_error(e.message + "6");}__fest_buf+=("</div><div class=\"score__value\">");try{__fest_buf+=(__fest_escapeHTML(score.score))}catch(e){__fest_log_error(e.message + "7");}__fest_buf+=("</div></div>");}__fest_select="btn";__fest_params={};try{__fest_params={title: 'Назад', href: '#'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);(function(__fest_context){__fest_blocks.signup=function(params){var __fest_buf="";__fest_buf+=("<div class=\"signup\">");__fest_select="form";__fest_params={};try{__fest_params={type: 'signup'}}catch(e){__fest_log_error(e.message)}__fest_fn=__fest_blocks[__fest_select];if (__fest_fn)__fest_buf+=__fest_call(__fest_fn,__fest_params,false);__fest_buf+=("</div>");return __fest_buf;};})(__fest_context);try{__fest_select=(params.block)}catch(e){__fest_select="";__fest_log_error(e.message)}__fest_params={};try{__fest_params=params}catch(e){__fest_log_error(e.message)}__fest_chunks.push(__fest_buf,{name:__fest_select,params:__fest_params,cp:false});__fest_buf="";__fest_to=__fest_chunks.length;if (__fest_to) {__fest_iterator = 0;for (;__fest_iterator<__fest_to;__fest_iterator++) {__fest_chunk=__fest_chunks[__fest_iterator];if (typeof __fest_chunk==="string") {__fest_html+=__fest_chunk;} else {__fest_fn=__fest_blocks[__fest_chunk.name];if (__fest_fn) __fest_html+=__fest_call(__fest_fn,__fest_chunk.params,__fest_chunk.cp);}}return __fest_html+__fest_buf;} else {return __fest_buf;}} ; });