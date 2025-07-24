from typing import Dict, List, Optional


class MemoryDatabase:
    """Banco de dados em memória para correspondências"""

    def __init__(self):
        self.correspondencias: Dict[str, Dict] = {}

    def create(self, codigo: str, data: Dict) -> Dict:
        """Cria uma nova correspondência"""
        if codigo in self.correspondencias:
            raise ValueError("Código já existe")

        self.correspondencias[codigo] = data
        return data

    def get(self, codigo: str) -> Optional[Dict]:
        """Busca uma correspondência pelo código"""
        return self.correspondencias.get(codigo)

    def get_all(self) -> List[Dict]:
        """Retorna todas as correspondências"""
        return list(self.correspondencias.values())

    def delete(self, codigo: str) -> Optional[Dict]:
        """Remove uma correspondência pelo código"""
        return self.correspondencias.pop(codigo, None)

    def exists(self, codigo: str) -> bool:
        """Verifica se uma correspondência existe"""
        return codigo in self.correspondencias

    def count(self) -> int:
        """Retorna o número total de correspondências"""
        return len(self.correspondencias)

    def clear(self) -> None:
        """Limpa todas as correspondências"""
        self.correspondencias.clear()
