fetch("/Projekt_M133_Military_ranking-1.0/resource/weapon/list")
    .then((response) => {
        if (response.status == 403) {
            document.querySelector("#weapons").innerHTML = "";
            document.querySelector("#message").innerHTML =
                "You are not authorized to view this page.";
            throw new Error("Authorization failed");
        }
        return response.json();
    })
    .then((weapons) => {
        const weaponList = document.querySelector("#weapons");
        weapons.forEach((weapon) => {
            const row = document.createElement("tr");
            row.innerHTML = `
					<td>${weapon.weaponID}</td>
					<td>${weapon.weaponName}</td>
					<td>${weapon.secureCode}</td>
					<td>${weapon.battlepoints}</td>
				`;
            weaponList.appendChild(row);
        });
        weaponList.style.display = "table";
    });
