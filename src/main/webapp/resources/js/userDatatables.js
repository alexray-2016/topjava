var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

function changeUserStatus(checkbox) {
    var enabled = checkbox.is(":checked");
    checkbox.parent().parent().css("text-decoration", enabled ? "none" : "line-through");
    $.ajax({
        url: ajaxUrl + checkbox.attr('id'),
        type: 'POST',
        success: function () {
            updateTable();
            successNoty(checkbox.attr('checked') ? 'Disabled' : 'Enabled')
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateTableByData(data);
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});