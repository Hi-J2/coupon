'use strict';
$(document).ready(() => {

    $('#couponTable').DataTable({
        processing: true,
        serverSide: true,
        ordering: false,
        searching: false,
        lengthChange: false,
        language: {
            emptyTable: "발급된 쿠폰이 없습니다.",
            processing: "처리중 - 잠시만 기다려 주세요.",
            loadingRecords: "잠시만 기다려 주세요.",
            paginate: {
                first: "처음",
                last: "마지막 ",
                next: "다음",
                previous: "이전"
            }
        },
        ajax: function (data, callback, settings) {
            let page = Math.floor(data.start / data.length);

            couponApi.findAll({page: page},
                function (message, response) {
                    if (message != null) {
                        alert(message);
                        return false;
                    }
                    callback({
                        recordsTotal: response.data.totalElements,
                        recordsFiltered: response.data.totalElements,
                        data: response.data.content
                    });
                });
        },
        infoCallback: function (settings, start, end, max, total, pre) {
            let api = this.api();
            let pageInfo = api.page.info();

            return 'Page ' + (pageInfo.page + 1) + ' of ' + pageInfo.pages;
        },
        columns: [
            {data: "id"},
            {data: "email"},
            {
                data: "code", render: function (data, type, row, meta) {
                    return  data.substr(0, 4) + "-" +
                            data.substr(4, 4) + "-" +
                            data.substr(8, 4) + "-" +
                            data.substr(12, 4);
                }
            },
            {data: "status"},
            {data: "createdAt"}
        ]
    });


    $('#couponForm').submit((e) => {
        e.preventDefault();
        if (!confirm("발급 하시겠습니까?")) {
            return false;
        }
        let email = $('#email').val();

        if(!commonUtil.checkEmail(email)){
            alert("이메일 형식이 잘못되었습니다.");
            return false;
        }

        let param = {
            email: email
        };

        couponApi.create(param, (message, response) => {
            if (message != null) {
                alert("등록에 실패하였습니다."
                    + "\nCODE : " + response.code
                    + "\nmessage : " + message);
                return false;
            }
            $('#couponTable').DataTable().ajax.reload(null, false);
        });
    });

});