from typing import Dict, List
from fastapi import HTTPException

from models import Carta, Encomenda, Telegrama
from database import db


class CorrespondenciaService:
    """Serviço para gerenciar correspondências"""

    def calcular_preco_carta(self, selada: bool) -> float:
        """Calcula o preço de uma carta"""
        return 2.00 if selada else 1.50

    def calcular_preco_encomenda(self, peso: float) -> float:
        """Calcula o preço de uma encomenda"""
        return peso * 5.00

    def calcular_preco_telegrama(self, numero_palavras: int) -> float:
        """Calcula o preço de um telegrama"""
        return numero_palavras * 0.50

    def registrar_carta(self, carta: Carta) -> Dict:
        """Registra uma nova carta"""
        if db.exists(carta.codigo):
            raise HTTPException(status_code=400, detail="Código já existe")

        preco = self.calcular_preco_carta(carta.selada)

        data = {
            "codigo": carta.codigo,
            "destinatario": carta.destinatario,
            "endereco": carta.endereco,
            "tipo": "carta",
            "selada": carta.selada,
            "preco": preco,
        }

        db.create(carta.codigo, data)

        return {
            "message": "Carta registrada com sucesso",
            "correspondencia": data,
        }

    def registrar_encomenda(self, encomenda: Encomenda) -> Dict:
        """Registra uma nova encomenda"""
        if db.exists(encomenda.codigo):
            raise HTTPException(status_code=400, detail="Código já existe")

        preco = self.calcular_preco_encomenda(encomenda.peso)

        data = {
            "codigo": encomenda.codigo,
            "destinatario": encomenda.destinatario,
            "endereco": encomenda.endereco,
            "tipo": "encomenda",
            "peso": encomenda.peso,
            "preco": preco,
        }

        db.create(encomenda.codigo, data)

        return {
            "message": "Encomenda registrada com sucesso",
            "correspondencia": data,
        }

    def registrar_telegrama(self, telegrama: Telegrama) -> Dict:
        """Registra um novo telegrama"""
        if db.exists(telegrama.codigo):
            raise HTTPException(status_code=400, detail="Código já existe")

        preco = self.calcular_preco_telegrama(telegrama.numero_palavras)

        data = {
            "codigo": telegrama.codigo,
            "destinatario": telegrama.destinatario,
            "endereco": telegrama.endereco,
            "tipo": "telegrama",
            "numero_palavras": telegrama.numero_palavras,
            "preco": preco,
        }

        db.create(telegrama.codigo, data)

        return {
            "message": "Telegrama registrado com sucesso",
            "correspondencia": data,
        }

    def listar_correspondencias(self) -> Dict:
        """Lista todas as correspondências"""
        correspondencias = db.get_all()
        return {
            "total": db.count(),
            "correspondencias": correspondencias,
        }

    def consultar_correspondencia(self, codigo: str) -> Dict:
        """Consulta uma correspondência pelo código"""
        correspondencia = db.get(codigo)
        if not correspondencia:
            raise HTTPException(
                status_code=404, detail="Correspondência não encontrada"
            )

        return correspondencia

    def consultar_preco(self, codigo: str) -> Dict:
        """Consulta o preço de uma correspondência"""
        correspondencia = db.get(codigo)
        if not correspondencia:
            raise HTTPException(
                status_code=404, detail="Correspondência não encontrada"
            )

        return {"codigo": codigo, "preco": correspondencia["preco"]}

    def entregar_correspondencia(self, codigo: str) -> Dict:
        """Remove correspondência do sistema (entrega)"""
        correspondencia = db.delete(codigo)
        if not correspondencia:
            raise HTTPException(
                status_code=404, detail="Correspondência não encontrada"
            )

        return {
            "message": "Correspondência entregue com sucesso",
            "correspondencia": correspondencia,
        }
