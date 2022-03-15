document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('delete').addEventListener('click', function(e) {
        if(!window.confirm('本当に削除しますか？')) {
            e.preventDefault();
        } else {
            // 処理なし
        }
    }, false);
}, false);

