$(function() {
	$('.index-pagination').paginathing({		// 親要素のclassを記述
		perPage:	3,							// 1ページあたりの表示件数
		limitPagination:5,						// ページネーション数を制限
		prevText:	'前へ',						// 1つ前のページへ移動するボタンのテキスト
		nextText:	'次へ',						// 1つ次のページへ移動するボタンのテキスト
		firstText:	'最初へ',					// 「最初のボタン」テキスト
		lastText:	'最後へ',					// 「最後のボタン」のテキスト
		pageNumbers: true						// 総ページ数のうち現在のページ数を表示
	})
});
