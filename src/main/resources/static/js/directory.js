const joinedButton = document.querySelector('#joined-button');
const fullScreenWidthMin = 992;

function disablePageScroll() {
    // Prevent page scrolling and avoid layout shift by preserving scroll position
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';
}

function enablePageScroll() {
    document.documentElement.style.overflow = '';
    document.body.style.overflow = '';
}

function openPreview(el) {
    const overlay = document.getElementById("overlay");
    const previewInner = document.getElementById("preview-inner");

    // Populate the inner container so the static close button remains
    previewInner.innerHTML = "";
    const clone = el.cloneNode(true);

    const metaDesc = clone.querySelector('.meta-desc');
    if (metaDesc) {
        const full = metaDesc.getAttribute('data-full-description');
        if (full) {
            metaDesc.textContent = full;
        }
    }

    previewInner.appendChild(clone);

    overlay.style.display = "flex";
    disablePageScroll();
}

function closePreview() {
    document.getElementById("overlay").style.display = "none";
    enablePageScroll();
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        const overlay = document.getElementById("overlay");
        if (overlay && overlay.style.display === 'flex') {
            closePreview();
        }
    }
});

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.group-card').forEach(groupCard => {
        groupCard.addEventListener('click', function (e) {
            const target = e.target;
            if (target.closest('a') || target.closest('button') || target.classList.contains('toggle')) {
                return;
            }

            openPreview(groupCard);
        })
    })

    // Prevent clicks inside the preview from bubbling up to the overlay which would close it
    const previewEl = document.getElementById('preview');
    if (previewEl) {
        previewEl.addEventListener('click', function (e) {
            e.stopPropagation();
        });
    }

    // Only attach the joined button handler if the element exists to avoid errors
    if (joinedButton) {
        joinedButton.addEventListener('click', function () {
            joinedButton.classList.toggle('btn-outline-light');
            joinedButton.classList.toggle('btn-light')
        })
    }

    document.querySelectorAll('.toggle').forEach(toggle => {
        toggle.addEventListener('click', function () {
            toggle.classList.toggle('on');
        });
    });
});