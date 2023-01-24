--индексы
CREATE INDEX IF NOT EXISTS idx_fts_contract ON "Contract"
    USING gin (make_tsvector("ContractSubject"));
--индексы
CREATE INDEX IF NOT EXISTS idx_fts_supplier ON "Supplier"
    USING gin (make_tsvector("SupplierName"));


-- Проверяет, есть ли искомый ИНН в базке

CREATE OR REPLACE FUNCTION check_inn_exists(inn varchar)
    RETURNS BOOLEAN AS
$$
BEGIN
    RETURN (SELECT (SELECT COUNT(*) FROM "Supplier" WHERE "INNSupplier" = inn) > 0);
END;
$$ LANGUAGE plpgsql;

-- Использование
SELECT check_inn_exists('77159539');

-- Cчитает количество подходящих записей по определенному ИНН
CREATE OR REPLACE FUNCTION count_contracts_by_inn_and_query(inn varchar, query varchar)
    RETURNS INTEGER AS
$$
BEGIN
    RETURN (SELECT count(*)
            FROM (SELECT "Supplier_id", "SupplierName", "ContractSubject"
                  FROM "Contract"
                           JOIN "Supplier" S ON S."INNSupplier" = "Contract"."Supplier_id"
                  WHERE "Supplier_id" = inn
                    AND make_tsvector("ContractSubject") @@ websearch_to_tsquery('russian', query)
                  GROUP BY "Supplier_id", "ContractSubject", "SupplierName") as SiSNCS);
END;
$$ LANGUAGE plpgsql;

--Пример
SELECT count_contracts_by_inn_and_query('370252519855', 'Мебель в школе');

--Ищет всех саплаеров с ранком и каунтом по квери
CREATE OR REPLACE FUNCTION search_contracts(String varchar)
    RETURNS TABLE
            (
                Supplier_id     varchar,
                ContractSubject varchar,
                ContractSumm    numeric,
                date            varchar,
                rank            double precision,
                count           integer
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT DISTINCT "Supplier_id",
                        "ContractSubject",
                        "ContractSumm",
                        "ContractDate",
                        ts_rank_cd(make_tsvector("ContractSubject"), search, 1) * 1000 AS rank,
                        count_contracts_by_inn_and_query("Supplier_id", String) as count
        FROM "Contract"
                 join "Supplier" on "Contract"."Supplier_id" = "Supplier"."INNSupplier",
             websearch_to_tsquery('russian', String) search
        WHERE make_tsvector("ContractSubject") @@ search
        GROUP BY "Supplier_id", "ContractSumm", search, "ContractSubject", "ContractDate"
        ORDER BY  count DESC, rank DESC, "ContractSumm" DESC, "ContractDate" DESC;
END;
$$
    LANGUAGE plpgsql;

-- Применение
SELECT *
FROM search_contracts('Мебель в школу');