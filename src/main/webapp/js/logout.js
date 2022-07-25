document.getElementById("1").addEventListener("click", (e) => {
    e.preventDefault();
    const url = "./resource/user/logout";
    const options = {
        method: "DELETE"
    };
    fetch(url, options)
        .then((response) => {
            if (response.status == 200) {
                window.location.href = "./index.html";
            }
        })
        .catch((error) => {
            console.log(error);
        });
});
