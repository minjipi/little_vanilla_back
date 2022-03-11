function showHidden() {

    var toggle_view = document.getElementById('toggle_view');
    toggle_view.hidden;

    var toggle_target = document.getElementById('toggle_target');
    toggle_target.setAttribute('class', '');
    // toggle_target.remove('hidden');

    isShowMore = true;

};


function selectAll(element) {
    const checkboxes
        = document.querySelectorAll(".check");

    checkboxes.forEach((checkbox) => {
        checkbox.checked = element.checked;
    })
}