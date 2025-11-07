async function calcular() {
  const investido = document.getElementById("investido").value.replace(",", ".");
  const compra = document.getElementById("compra").value.replace(",", ".");
  const atual = document.getElementById("atual").value.replace(",", ".");

  if (!investido || !compra || !atual) {
    alert("Preencha todos os campos!");
    return;
  }

  const data = { investido: parseFloat(investido), compra: parseFloat(compra), atual: parseFloat(atual) };

  const res = await fetch("http://localhost:8080/calcular", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });

  const resultado = await res.json();

  document.getElementById("resultado").innerHTML = `
    <p>💎 Quantidade de ativos: ${resultado.quantidade.toFixed(2)}</p>
    <p>💰 Valor atual: R$ ${resultado.valorAtual.toFixed(2)}</p>
    <p>📈 Lucro: R$ ${resultado.lucro.toFixed(2)}</p>
  `;
}
