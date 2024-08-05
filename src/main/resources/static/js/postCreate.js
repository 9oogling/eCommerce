$(document).ready(function () {
    function getToken() {
        return Cookies.get('Authorization');
    }
    const auth = getToken();


    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        jqXHR.setRequestHeader('Authorization', auth);
    });

        $('#createPostForm').on('submit', function (e) {
            e.preventDefault(); // 기본 폼 제출 방지

            var formData = new FormData();
            var seasons = [];
            $('input[name="season"]:checked').each(function () {
                seasons.push($(this).val());
            });
            var gender = $('input[name="gender"]:checked').val();
            // 성별과 시즌을 조합하여 categoryName 생성
            var categoryName = [];
            if (gender) {
                categoryName.push(gender);
            }
            categoryName = categoryName.concat(seasons);

            var categoryNameString = categoryName.join(',');

            formData.append('request', new Blob([JSON.stringify({
                title: $('#title').val(),
                content: $('#content').val(),
                price: parseInt($('#price').val()),
                saleType: $('#saleType').val(),
                hashtags:  Array.from($('#hashtagContainer .hashtag').map(function () { return $(this).text(); })).join(','),
                categoryName: categoryNameString
            })], {type: "application/json"}));

            var imageFiles = $('#image')[0].files;
            for (var i = 0; i < imageFiles.length; i++) {
                formData.append('image', imageFiles[i]);
            }

            var productImageFiles = $('#productImage')[0].files;
            for (var i = 0; i < productImageFiles.length; i++) {
                formData.append('productImage', productImageFiles[i]);
            }


        $.ajax({
            url: '/api/posts', // 게시물 생성 API 엔드포인트
            type: 'POST',
            data: formData,
            contentType: false, // multipart/form-data로 설정
            processData: false, // data를 문자열로 변환하지 않음
            success: function (response) {
                alert('게시물이 성공적으로 작성되었습니다.');
                console.log(response);
                window.location.href = '/posts';
            },
            error: function (xhr, status, error) {
                alert('게시물 생성에 실패했습니다.');
                console.error(xhr, status, error);
            }
        });
    });


    // 선택 사항: 업로드 전에 이미지 미리보기
    function previewImage(event, previewElementId) {
        const file = event.target.files[0];
        const previewContainer = document.getElementById(previewElementId);
        previewContainer.innerHTML = '';
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.maxWidth = '100%';
                img.style.marginTop = '10px';
                previewContainer.appendChild(img);
            }
            reader.readAsDataURL(file);
        }
    }

    document.getElementById('image').addEventListener('change', function (e) {
        previewImage(e, 'imagePreview');
    });

    document.getElementById('productImage').addEventListener('change', function (e) {
        previewImage(e, 'productImagePreview');
    });
});


function submit() {
    const salesType = document.getElementById('salesType').value;

    if (!salesType) {
        alert("판매 유형을 선택해주세요.");
        return;
    }

    const data = {
        saleType: salesType
        // 추가 필드 필요 시 여기에 추가
    };

}


const hashtagsInput = document.getElementById('hashtags');
const hashtagContainer = document.getElementById('hashtagContainer');

hashtagsInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter' || event.key === ',' || event.key === ' ') {
        event.preventDefault(); // 기본 동작 방지 (엔터/쉼표/공백 삽입 방지)
        const hashtagText = hashtagsInput.value.trim();
        if (hashtagText) {
            createHashtag(hashtagText);
            hashtagsInput.value = ''; // 입력 필드 초기화
        }
    }
});

function createHashtag(text) {
    const hashtagElement = document.createElement('div');
    hashtagElement.className = 'hashtag';
    hashtagElement.textContent = text;

    const removeButton = document.createElement('span');
    removeButton.addEventListener('click', () => {
        hashtagContainer.removeChild(hashtagElement);
    });

    hashtagElement.appendChild(removeButton);
    hashtagContainer.appendChild(hashtagElement);
}

