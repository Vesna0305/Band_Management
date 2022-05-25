ALTER TABLE tracks DROP COLUMN video_status;

ALTER TABLE tracks ADD video_status BIT(1) NOT NULL;