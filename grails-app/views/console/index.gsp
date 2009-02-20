<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
   <meta http-equiv="Content-type" content="text/html; charset=utf-8">
   <title>Grails Web Console</title>
   <style type="text/css" media="screen">
      body, html {
         margin: 0;
         padding: 0;
         font-family: "Lucida Grande", Verdana, sans-serif;
         background: #eee;
      }
      h1 {
         margin: 0px;
         padding: 4px;
         font-size: 15px;
         background: #333;
         color: #fff;
      }
      #code {
         border-bottom: 1px solid #333;
      }
      #result {
         padding: 3px;
      }
      .console-editor {
         border-bottom: 1px solid #333;
         background: #fff;
      }
      .stacktrace {
         color: red;
         background: #ddd;
      }
      .script-result {
         color: blue;
         background: #ddd;
      }
   </style>
   <script src="${createLinkTo(dir:'js/codemirror/js', file: 'codemirror.js')}" type="text/javascript" charset="utf-8"></script>
   <script src="${createLinkTo(dir:'js/codemirror/js', file: 'mirrorframe.js')}" type="text/javascript" charset="utf-8"></script>

</head>
<body>
   <h1>Grails Console</h1>
   <div class="console-editor">
      <textarea id="code" style="width: 100%; height: 300px;" class="codepress java" rows="25" cols="100">// Groovy Code here</textarea>
            <button id="submit">Execute</button>
      <button id="clear">Clear Results</button>
      <span id="progress" style="display: none;">
         <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" style="border: 0;" align="absmiddle" alt="" />
         Executing Script...
      </span>
   </div>
   <div id="result">
      <div style="color:#ddd">Script Output...</div>
   </div>
   <g:javascript library="scriptaculous" />
   <script type="text/javascript" charset="utf-8">

      Event.observe(window, "dom:loaded", function(){

         var textarea = $('code');
         var editor = new MirrorFrame(CodeMirror.replace(textarea), {
            height: "300px",
            content: textarea.value,
            parserfile: ["tokenizegroovy.js", "parsegroovy.js"],
            stylesheet: "${createLinkTo(dir:'js/codemirror', file: 'css/jscolors.css')}",
            path: "${createLinkTo(dir:'js/codemirror/js')}/",
            autoMatchParens: true,
            dumbTabs: true
         });

         var submitButton = $('submit');
         var resultContainer = $('result');

         submitButton.observe("click", function() {
            $('progress').show();
            new Ajax.Request("${createLink(action: 'execute')}", {
               parameters: {
                  code: editor.mirror.getCode().strip()
               },
               onSuccess: function(response, json) {
                  resultContainer.appendChild(
                     new Element('pre').update(response.responseText)
                  );
                  $('progress').hide();
               }
            });

         });
         $('clear').observe("click", function() {
            resultContainer.update("");
         });
      });

   </script>
</body>
</html>