/*
================================================================================
File: src/main/resources/data.sql
Description: Seed data for Credit Cards in PostgreSQL.
================================================================================
*/
-- Use ON CONFLICT to avoid errors if the service is restarted
INSERT INTO credit_cards (id, customer_id, card_number, card_name, total_due, min_due, due_date) VALUES
('CC-001', 'CUST-0001', '4111-1111-1111-1111', 'Platinum Rewards Card', 15250.75, 1525.00, '2025-07-20'),
('CC-002', 'CUST-0005', '4222-2222-2222-2222', 'Millennia Card', 8500.00, 850.00, '2025-07-22'),
('CC-003', 'CUST-0009', '4333-3333-3333-3333', 'Ultimate Cashback Card', 22500.50, 2250.00, '2025-07-18'),
('CC-004', 'CUST-0018', '4444-4444-4444-4444', 'Platinum Rewards Card', 5400.00, 540.00, '2025-07-25')
ON CONFLICT (card_number) DO NOTHING;

