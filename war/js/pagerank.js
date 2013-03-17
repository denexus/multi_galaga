(
    $.fn.PageRank = function(callback)
    {
        var _library = new Object();

        //Creat the system library
        _library.parseUrl = function(a)
        {
            var b = {};
            var a = a || '';
            /*
                * parse the url to extract its parts
            */
            if (a = a.match(/((s?ftp|https?):\/\/){1}([^\/:]+)?(:([0-9]+))?([^\?#]+)?(\?([^#]+))?(#(.+))?/)) {
                b.scheme    = a[2]  ? a[2]  : "http";
                b.host      = a[3]  ? a[3]  : null;
                b.port      = a[5]  ? a[5]  : null;
                b.path      = a[6]  ? a[6]  : null;
                b.args      = a[8]  ? a[8]  : null;
                b.anchor    = a[10] ? a[10] : null
            }
            return b
        }

        _library.ValidUrl = function(url)
        {
            var b = true;
            return b = url.host === undefined ? false : url.scheme != "http" && url.scheme != "https" ? false : url.host == "localhost" ? false : true
        }

        _library.toHex = function(a){
            return (a < 16 ? "0" : "") + a.toString(16)
        }

        _library.hexEncodeU32 = function(a) {
        }

        _library.generateHash = function(a)
        {
            for (var b = 16909125, c = 0; c < a.length; c++)
            {
            }
            return _library.hexEncodeU32(b)
        }

        var CheckPageRank = function(domain,_call)
        {
            var hash = _library.generateHash(domain);
            $.ajax(
            {
                url: 'http://www.google.com/search?client=navclient-auto&ch=8'+hash+'&features=Rank&q=info:' + escape(domain),
                async: true,
                dataType: 'html',
                ifModified:true,
                contentType:'',
                type:'GET',
                beforeSend:function(xhr)
                {
                    xhr.setRequestHeader('Referer','http://google.com/'); //Set Referer
                },
                success: function(content,textS,xhr){
                    var d = xhr.responseText.substr(9, 2).replace(/\s$/, "");
                    if (d == "" || isNaN(d * 1)) d = "0";
                    _call(d);
                }
            });
        }
        //Return the callback
        $(this).each(function(){
            urlsegments = _library.parseUrl($(this).attr('href'))
            if(_library.ValidUrl(urlsegments))
            {
                CheckPageRank(urlsegments.host,function(rank){
                    alert(rank)
                    callback(rank);
                });
            }
        });
        return this; //Dont break any chain.
    }
)(jQuery);