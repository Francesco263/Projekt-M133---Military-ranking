document.addEventListener("DOMContentLoaded", () => {
	document.querySelector("#loginform").addEventListener("submit", (e) => {
		e.preventDefault();
		const username = document.querySelector("#username").value;
		const password = document.querySelector("#password").value;
		const url = "./resource/user/login";
		const options = {
			method: "POST",
			headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: `username=${username}&password=${password}`,
        };
		fetch(url, options)
			.then((response) => {
				if (response.status == 200) {
					//saveToken(response.headers);
					window.location.href = "./loggedIn.html";
				} else {
					document.querySelector("#message").innerHTML = "Invalid username or password";
				}
			})
			.catch((error) => {
				console.log(error);
			});
	});
});
