from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Dict, Optional, Union
import uvicorn

app = FastAPI(
    title="Sistema de Correios API",
    description="API para gerenciamento de correspondências",
)


# Modelos de dados
class Correspondencia(BaseModel):
    codigo: str
    destinatario: str
    endereco: str
    tipo: str


class Carta(Correspondencia):
    selada: bool
    tipo: str = "carta"


class Encomenda(Correspondencia):
    peso: float
    tipo: str = "encomenda"


class Telegrama(Correspondencia):
    numero_palavras: int
    tipo: str = "telegrama"


class CorrespondenciaResponse(BaseModel):
    codigo: str
    destinatario: str
    endereco: str
    tipo: str
    preco: float
    detalhes: Dict


# Armazenamento em memória
correspondencias: Dict[str, Dict] = {}


@app.get("/")
async def root():
    return {"message": "Sistema de Correios API"}


@app.get("/loja/nome")
async def obter_nome_loja():
    """Retorna o nome da loja"""
    return {"nome": "Correios UFC - Sistema Distribuído"}


@app.post("/correspondencias/carta")
async def registrar_carta(carta: Carta):
    """Registra uma nova carta"""
    if carta.codigo in correspondencias:
        raise HTTPException(status_code=400, detail="Código já existe")

    preco = 2.00 if carta.selada else 1.50

    correspondencias[carta.codigo] = {
        "codigo": carta.codigo,
        "destinatario": carta.destinatario,
        "endereco": carta.endereco,
        "tipo": "carta",
        "selada": carta.selada,
        "preco": preco,
    }

    return {
        "message": "Carta registrada com sucesso",
        "correspondencia": correspondencias[carta.codigo],
    }


@app.post("/correspondencias/encomenda")
async def registrar_encomenda(encomenda: Encomenda):
    """Registra uma nova encomenda"""
    if encomenda.codigo in correspondencias:
        raise HTTPException(status_code=400, detail="Código já existe")

    preco = encomenda.peso * 5.00

    correspondencias[encomenda.codigo] = {
        "codigo": encomenda.codigo,
        "destinatario": encomenda.destinatario,
        "endereco": encomenda.endereco,
        "tipo": "encomenda",
        "peso": encomenda.peso,
        "preco": preco,
    }

    return {
        "message": "Encomenda registrada com sucesso",
        "correspondencia": correspondencias[encomenda.codigo],
    }


@app.post("/correspondencias/telegrama")
async def registrar_telegrama(telegrama: Telegrama):
    """Registra um novo telegrama"""
    if telegrama.codigo in correspondencias:
        raise HTTPException(status_code=400, detail="Código já existe")

    preco = telegrama.numero_palavras * 0.50

    correspondencias[telegrama.codigo] = {
        "codigo": telegrama.codigo,
        "destinatario": telegrama.destinatario,
        "endereco": telegrama.endereco,
        "tipo": "telegrama",
        "numero_palavras": telegrama.numero_palavras,
        "preco": preco,
    }

    return {
        "message": "Telegrama registrado com sucesso",
        "correspondencia": correspondencias[telegrama.codigo],
    }


@app.get("/correspondencias")
async def listar_correspondencias():
    """Lista todas as correspondências cadastradas"""
    return {
        "total": len(correspondencias),
        "correspondencias": list(correspondencias.values()),
    }


@app.get("/correspondencias/{codigo}")
async def consultar_correspondencia(codigo: str):
    """Consulta uma correspondência pelo código"""
    if codigo not in correspondencias:
        raise HTTPException(status_code=404, detail="Correspondência não encontrada")

    return correspondencias[codigo]


@app.get("/correspondencias/{codigo}/preco")
async def consultar_preco(codigo: str):
    """Consulta o preço de uma correspondência pelo código"""
    if codigo not in correspondencias:
        raise HTTPException(status_code=404, detail="Correspondência não encontrada")

    return {"codigo": codigo, "preco": correspondencias[codigo]["preco"]}


@app.delete("/correspondencias/{codigo}")
async def entregar_correspondencia(codigo: str):
    """Remove correspondência do sistema (entrega)"""
    if codigo not in correspondencias:
        raise HTTPException(status_code=404, detail="Correspondência não encontrada")

    correspondencia_entregue = correspondencias.pop(codigo)

    return {
        "message": "Correspondência entregue com sucesso",
        "correspondencia": correspondencia_entregue,
    }


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
